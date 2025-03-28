1.  Before build of project check if "src/main/resources/META-INF/services" contains
    "org.keycloak.authentication.AuthenticatorFactory" file, if not generate using following commands from project directory
    "$ mkdir -p src/main/resources/META-INF/services"
    "$ echo "com.sg.auth.ApprovalAuthenticatorFactory" > src/main/resources/META-INF/services/org.keycloak.authentication.AuthenticatorFactory"

2.  Put this built jar ino "/opt/keycloak/providers" of keycloak containers

3.  Enter container and execute following commands
    "$ /opt/keycloak/bin/kc.sh build"
    On success without any error restart the container
