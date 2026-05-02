# DeepEyeMusicPro Architecture

DeepEyeMusicPro uses a layered Android architecture:

- `ui/`: fragments, adapters, bottom sheets, and view models
- `player/`: Media3 controller, queue, and player state
- `service/`: foreground playback and download services
- `extractor/`: search, link parsing, stream extraction, and HTTP downloader plumbing
- `adblock/`: isolated WebView fallback request blocking and cosmetic cleanup
- `dsp/`: preset model, JNI wrapper, Media3 audio processor, and manager
- `db/`: Room entities, DAOs, converters, and database
- `repository/`: app, player, search, download, settings, and DSP repositories
- `model/`: shared immutable state and domain data classes
- `util/`: file, time, network, logging, and UI state helpers

`DeepEyeApp` owns long-lived infrastructure: notification channels, database, repositories, and the application coroutine scope. UI screens stay thin and call repositories or player controllers.

Room persists cached tracks, favorites, search history, DSP presets, downloaded-track metadata, playlists, and playlist-track ordering via `playlist_tracks`.
