package com.ekremc.spotifyarchive.service.authorization;

import com.ekremc.spotifyarchive.service.spotify.SpotifyAPIClient;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorizationCodeRefreshService {
    Logger logger = LoggerFactory.getLogger(AuthorizationCodeRefreshService.class);

    private final SpotifyAPIClient spotifyAPIClient;

    public AuthorizationCodeRefreshService(SpotifyAPIClient spotifyAPIClient) {
        this.spotifyAPIClient = spotifyAPIClient;
    }

    @Scheduled(cron = "0 30 * * * *", zone = "Europe/Istanbul")
    private void authorizationCodeRefresh_Sync() {
        try {
            AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyAPIClient.api()
                    .authorizationCodeRefresh()
                    .build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            spotifyAPIClient.api().setAccessToken(authorizationCodeCredentials.getAccessToken());
            final String codeCredentialsRefreshToken = authorizationCodeCredentials.getRefreshToken();
            if (codeCredentialsRefreshToken != null && !codeCredentialsRefreshToken.equals(spotifyAPIClient.api().getRefreshToken())) {
                spotifyAPIClient.api().setRefreshToken(codeCredentialsRefreshToken);
            }
        } catch (IOException | SpotifyWebApiException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
