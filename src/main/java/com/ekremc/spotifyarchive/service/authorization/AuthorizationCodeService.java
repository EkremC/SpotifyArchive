package com.ekremc.spotifyarchive.service.authorization;

import com.ekremc.spotifyarchive.service.spotify.SpotifyAPIClient;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorizationCodeService {
    Logger logger = LoggerFactory.getLogger(AuthorizationCodeService.class);

    private final SpotifyAPIClient spotifyAPIClient;

    @Autowired
    public AuthorizationCodeService(SpotifyAPIClient spotifyAPIClient) {
        this.spotifyAPIClient = spotifyAPIClient;
    }

    public void authorizationCode(final String code) {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyAPIClient.api().authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyAPIClient.api().setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyAPIClient.api().setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        } catch (IOException | SpotifyWebApiException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
