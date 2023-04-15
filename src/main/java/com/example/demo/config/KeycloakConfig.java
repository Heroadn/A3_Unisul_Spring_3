package com.example.demo.config;

import com.example.demo.model.Usuario;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.token.TokenManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
@Configuration
public class KeycloakConfig {
    private String serverUrl;

    private String realm;

    private String username;

    private String password;

    private String clientId;

    public KeycloakConfig() {
    }

    public Keycloak getInstance() {
        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .build();
    }

    //wrapper to getInstance.realm(realm)
    public RealmResource realm() {
        return getInstance().realm(realm);
    }

    public TokenManager tokenManager(Usuario usuario)
    {
        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .clientId(clientId)
                .build().tokenManager();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}