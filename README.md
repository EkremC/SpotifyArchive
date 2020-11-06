# Spotify Archive
Helps you to archive tracks from your Discover Weekly and Release Radar into your archive playlists

### **Usage**
1) You have to create an app on the Spotify Developer page and get a client id and client secret.
2) The app doesn't create new archive playlists. You have to create them.
3) After the above steps are completed, you must edit the pom.xml file.
4) Start the application, then send a request to /spotify/authorize and allow the application.

It sends requests for Discovery Weekly and Release Radar at 5 am on Monday and Friday respectively.
It also sends a request to get a refresh token every 30 minutes.