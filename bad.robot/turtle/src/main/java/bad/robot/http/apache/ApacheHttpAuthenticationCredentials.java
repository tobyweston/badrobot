package bad.robot.http.apache;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;

public class ApacheHttpAuthenticationCredentials {

    private final AuthScope scope;
    private final UsernamePasswordCredentials user;

    public ApacheHttpAuthenticationCredentials(AuthScope scope, UsernamePasswordCredentials user) {
        this.scope = scope;
        this.user = user;
    }

    public AuthScope getScope() {
        return scope;
    }

    public UsernamePasswordCredentials getUser() {
        return user;
    }
}
