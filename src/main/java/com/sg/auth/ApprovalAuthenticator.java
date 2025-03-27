package com.sg.auth;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import jakarta.ws.rs.core.Response;

public class ApprovalAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String approved = user.getFirstAttribute("approved");

        if (approved == null) {
            // ✅ If "approved" attribute is absent, allow login
            context.success();
            return;
        }

        if ("true".equals(approved)) {
            // ✅ Clear approval after successful login to force re-approval next time
//            user.removeAttribute("approved");
            user.setSingleAttribute("approved", "false");
            context.success();
        } else {
            // ❌ User is not approved, deny access
            Response challengeResponse = Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("{\"error\": \"Login_Approval_Required\", \"error_description\": \"Administrative approval is required.\"}")
                    .type("application/json")
                    .build();

            context.failure(AuthenticationFlowError.ACCESS_DENIED, challengeResponse);
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // No additional action needed.
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // No required actions needed.
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void close() {
        // No cleanup needed.
    }
}
