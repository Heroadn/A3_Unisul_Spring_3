package com.example.demo.service;

import com.example.demo.config.KeycloakConfig;
import com.example.demo.model.Usuario;
import com.example.demo.utils.KeyCloakUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class KeycloakService {
    @Autowired
    private KeycloakConfig keycloak;

    //can be used to map token attributes user is created
    public static final class CustomTokenMapper
    {
        public static final String RESOURCE_ID = "resource-id";
    };

    /**
     * By default KeyCloak REST API doesn't allow to create account with credential type is PASSWORD,
     * it means after created account, need an extra step to make it works, it's RESET PASSWORD
     * @param usuario
     * @return
     */
    public int createAccount(final Usuario usuario) {
        Long resourceId = usuario.getID();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(usuario.getSenha());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(usuario.getNome());
        user.setFirstName("First name");
        user.setLastName("Last name");
        user.singleAttribute(
                CustomTokenMapper.RESOURCE_ID,
                Long.toString(resourceId));
        user.setCredentials(Arrays.asList(credential));
        user.setEmail(usuario.getEmail());

        //should be done by a router
        //but for now it just auto enables it
        user.setEnabled(true);
        user.setEmailVerified(true);

        //response
        javax.ws.rs.core.Response response = keycloak.realm().users().create(user);
        final int status = response.getStatus();

        if (status != HttpStatus.CREATED.value()) {
            return status;
        }

        // Reset password
        final String createdId = KeyCloakUtil.getCreatedId(response);
        CredentialRepresentation newCredential = new CredentialRepresentation();
        UserResource userResource = keycloak.realm().users().get(createdId);
        newCredential.setType(CredentialRepresentation.PASSWORD);
        newCredential.setValue(usuario.getSenha());
        newCredential.setTemporary(false);
        userResource.resetPassword(newCredential);
        return HttpStatus.CREATED.value();
    }

    public AccessTokenResponse getAccessToken(final Usuario usuario) {
        return keycloak.tokenManager(usuario).getAccessToken();
    }

    public String getRefreshToken(final Usuario usuario) {
        return getAccessToken(usuario).getRefreshToken();
    }
}