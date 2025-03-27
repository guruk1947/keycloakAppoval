package com.sg.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class ApprovalAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    private static final String PROVIDER_ID = "approval-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Approval-Based Token Issuance";
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ApprovalAuthenticator();
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Requires admin approval before a user can obtain a token.";
    }

    @Override
    public String getReferenceCategory() {
        return "Approval-Based";
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialization logic if needed.
    }

    @Override
    public void postInit(org.keycloak.models.KeycloakSessionFactory factory) {
        // Post-initialization logic if needed.
    }

    @Override
    public void close() {
        // Cleanup logic if needed.
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {  // ✅ Returns an array
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.CONDITIONAL,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }

}
