package com.example.demo.service;

import com.example.demo.config.KeycloakConfig;
import com.example.demo.model.Usuario;
import com.example.demo.utils.KeyCloakUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Service
public class KeycloakService {
    @Autowired
    private KeycloakConfig keycloak;

    //can be used to map token attributes user is created
    public static final class CustomTokenMapper
    {
        public static final String RESOURCE_ID = "resource-id";
    };

    //protocol/openid-connect/token
    public static class OpenidConnectResponse{
        public String access_token;
        public int expires_in;
        public int refresh_expires_in;
        public String refresh_token;
        public String token_type;
        public int not_before_policy;
        public String session_state;
        public String scope;
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

    //O Usuario é logout depois de atualizar as informações
    public void updateCredential(Usuario usuario, String oldEmail)
    {
        //obtendo detalhes do usuario, servidor de autenticação
        List<UserRepresentation> userRepresentationList
                = keycloak.realm().users().searchByEmail(oldEmail, true);

        UserRepresentation representation = userRepresentationList.get(0);
        String id = representation.getId();
        UserResource userResource = keycloak.realm().users().get(id);

        //atualizando email
        if(usuario.getEmail() != null && !usuario.getEmail().equals(oldEmail))
        {
            representation.setEmail(usuario.getEmail());
            representation.setFirstName(usuario.getNome());
            representation.setUsername(usuario.getNome());
            userResource.update(representation);
        }

        //atualizando senha
        if(usuario.getSenha() != null && !usuario.getSenha().equals(""))
        {
            System.out.println(usuario.getSenha());
            CredentialRepresentation newCredential = new CredentialRepresentation();
            newCredential.setType(CredentialRepresentation.PASSWORD);
            newCredential.setValue(usuario.getSenha());
            newCredential.setTemporary(false);
            userResource.resetPassword(newCredential);
        }
    };

    public UserResource getUser(String id)
    {
        return keycloak.getInstance().realm(keycloak.getRealm()).users().get(id);
    }

    public AccessTokenResponse getAccessToken(final Usuario usuario) {
        return keycloak.tokenManager(usuario).getAccessToken();
    }

    public String getRefreshToken(final Usuario usuario) {
        return getAccessToken(usuario).getRefreshToken();
    }

    public String requestAccessToken(String refreshToken)
    {
        //http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token
        final String uri
                = keycloak.getServerUrl() + "/realms/" + keycloak.getRealm()
                +"/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", keycloak.getClientId());
        map.add("grant_type","refresh_token");
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<OpenidConnectResponse> response =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        entity,
                        OpenidConnectResponse.class);

        return response.getBody().access_token;
    }

    public String revokeRefreshToken(Usuario usuario)
    {
        return revokeRefreshToken(getRefreshToken(usuario));
    }

    public void removeUser(Usuario usuario)
    {
        List<UserRepresentation> userRepresentationList
                = keycloak.realm().users().searchByEmail(usuario.getEmail(), true);

        UserRepresentation representation = userRepresentationList.get(0);
        String id = representation.getId();
        UserResource userResource = keycloak.realm().users().get(id);
        userResource.remove();
    }

    public String revokeRefreshToken(String refreshToken)
    {
        //http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token
        final String uri
                = keycloak.getServerUrl() + "/realms/" + keycloak.getRealm()
                +"/protocol/openid-connect/revoke";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", keycloak.getClientId());
        map.add("grant_type","refresh_token");
        map.add("token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<OpenidConnectResponse> response =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        entity,
                        OpenidConnectResponse.class);

        return "";
    }
}