package com.ekremc.spotifyarchive.controller;

import com.ekremc.spotifyarchive.service.authorization.AuthorizationCodeService;
import com.ekremc.spotifyarchive.service.authorization.AuthorizationCodeUriService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/spotify")
public class SpotifyController {

    private final AuthorizationCodeUriService authorizationCodeUriService;
    private final AuthorizationCodeService authorizationCodeService;

    public SpotifyController(final AuthorizationCodeUriService authorizationCodeUriService, final AuthorizationCodeService authorizationCodeService) {
        this.authorizationCodeUriService = authorizationCodeUriService;
        this.authorizationCodeService = authorizationCodeService;
    }

    @GetMapping(value = "/authorize")
    public RedirectView getAuthorizationCodeUri() {
        String codeUri = authorizationCodeUriService.authorizationCodeUri();
        return new RedirectView(codeUri);
    }

    @GetMapping(value = "/callback")
    public void callback(String code) {
        authorizationCodeService.authorizationCode(code);
    }
}