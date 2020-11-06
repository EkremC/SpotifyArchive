package com.ekremc.spotifyarchive.service.authorization;

import com.ekremc.spotifyarchive.service.spotify.SpotifyAPIClient;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class AuthorizationCodeUriService {

    private final AuthorizationCodeUriRequest authorizationCodeUriRequest;

    public AuthorizationCodeUriService(SpotifyAPIClient spotifyAPIClient) {
        authorizationCodeUriRequest = spotifyAPIClient.api().authorizationCodeUri()
                .scope("user-read-private, playlist-modify-private, playlist-modify-public")
                .show_dialog(true)
                .build();
    }

    public String authorizationCodeUri() {
        URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }
}
