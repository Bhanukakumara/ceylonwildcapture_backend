package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Service for verifying Google OAuth ID tokens.
 */
@Service
@Slf4j
public class GoogleAuthService {

    @Value("${google.client-id}")
    private String clientId;

    /**
     * Verifies a Google ID token and returns its payload.
     *
     * @param idTokenString the ID token string from the frontend
     * @return the token payload containing user information
     * @throws GeneralSecurityException if the token is invalid
     * @throws IOException            if there's a network error
     */
    public GoogleIdToken.Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            log.warn("Invalid Google ID token");
            throw new GeneralSecurityException("Invalid Google ID token");
        }
    }
}
