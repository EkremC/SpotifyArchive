package com.ekremc.spotifyarchive.service.spotify;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    Logger logger = LoggerFactory.getLogger(SpotifyService.class);

    @Value(value = "${spotify.discoverWeeklyId}")
    private String discoverWeeklyId;
    @Value(value = "${spotify.discoverWeeklyArchiveId}")
    private String discoverWeeklyArchiveId;
    @Value(value = "${spotify.releaseRadarId}")
    private String releaseRadarId;
    @Value(value = "${spotify.releaseRadarArchiveId}")
    private String releaseRadarArchiveId;
    private final SpotifyAPIClient spotifyAPIClient;

    public SpotifyService(SpotifyAPIClient spotifyAPIClient) {
        this.spotifyAPIClient = spotifyAPIClient;
    }

    @Scheduled(cron = "0 0 5 ? * MON", zone = "Europe/Istanbul")
    private void addDiscoveryWeeklySongs() {
        addSongsToPlaylist(discoverWeeklyId, discoverWeeklyArchiveId);
    }

    @Scheduled(cron = "0 0 5 * * FRI", zone = "Europe/Istanbul")
    private void addReleaseRadarSongs() {
        addSongsToPlaylist(releaseRadarId, releaseRadarArchiveId);
    }

    private void addSongsToPlaylist(String mainPlaylistId, String archivePlaylistId) {
        GetPlaylistsTracksRequest request = spotifyAPIClient.api().getPlaylistsTracks(mainPlaylistId).build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = request.execute();
            final PlaylistTrack[] tracks = playlistTrackPaging.getItems();
            final String songUris = Arrays.stream(tracks)
                    .map(PlaylistTrack::getTrack)
                    .map(Track::getUri)
                    .collect(Collectors.joining(","));

            final AddTracksToPlaylistRequest addTracksToPlaylistRequest = new AddTracksToPlaylistRequest.Builder(spotifyAPIClient.api().getAccessToken())
                    .playlist_id(archivePlaylistId)
                    .uris(songUris)
                    .build();

            addTracksToPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
