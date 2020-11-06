package com.ekremc.spotifyarchive.service.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class SpotifyAPIClient {

    @Value(value = "${spotify.clientId}")
    private String clientId;
    @Value(value = "${spotify.clientSecretId}")
    private String clientSecretId;
    private final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify/callback/");
    private SpotifyApi spotifyApi = null;

    public SpotifyApi api() {
        if (spotifyApi == null) {
            spotifyApi = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecretId)
                    .setRedirectUri(redirectUri)
                    .build();
        }
        return spotifyApi;
    }
}
