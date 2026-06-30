<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" width="120" alt="4K Video Player Logo"/>
</p>

<h1 align="center">4K Video Player</h1>

<p align="center">
  A modern, native Android media player built with <b>Java</b> and <b>Media3 ExoPlayer</b>.<br/>
  Browse, play, and manage your local video and audio files — all in one app.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Min%20SDK-31-brightgreen?style=flat-square" alt="Min SDK 31"/>
  <img src="https://img.shields.io/badge/Target%20SDK-36-blue?style=flat-square" alt="Target SDK 36"/>
  <img src="https://img.shields.io/badge/Java-17-orange?style=flat-square" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-purple?style=flat-square" alt="MVVM"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=flat-square" alt="License"/>
</p>

---

## ✨ Features

- 🎬 **Video Player** — Full-screen video playback with custom overlay controls, seek bar, play/pause, next/prev, and auto-hiding controls
- 🎵 **Music Player** — Full-featured audio player with seek forward/backward, progress tracking, and haptic feedback
- 🔔 **Background Playback** — Music continues playing in the background via `MediaSessionService` with system notification controls
- 📱 **Mini Player** — Persistent mini player on the home screen showing the currently playing track
- 📂 **Media Scanner** — Automatically discovers all video and audio files on the device using `MediaStore`
- 🌗 **Dark Mode Support** — Includes night-mode drawable and color resources
- 📐 **Responsive Design** — Dimension resources for 6 screen size buckets (`sw240dp` → `sw720dp`)
- 🔐 **Runtime Permissions** — Handles `READ_MEDIA_AUDIO` and `READ_MEDIA_VIDEO` permissions (Android 13+)

---

## 🏗️ Architecture

The project follows the **MVVM** (Model-View-ViewModel) pattern with a clear separation of concerns:

```
com.rtechnologies.videoplayer/
├── activities/          — UI screens (Home, Video Player, Music Player)
├── fragments/           — Tab content (Recents, Music, Video)
├── viewmodels/          — LiveData holders (MusicVM, VideoVM, SessionVM, RecentsVM)
├── repo/                — Data repositories (MusicRepo, VideosRepo, RecentsRepo)
├── adapters/            — RecyclerView adapter with multi-type ViewHolders
├── model/               — Data models (MediaModel)
├── services/            — MediaSessionService + Notification helpers
├── room/                — Room database (schema + DAO for play history)
├── utils/               — Utilities (ExoPlayer, MediaProvider, Permissions, etc.)
├── interfaces/          — Callback interfaces
├── constants/           — Enums for fragment IDs, intent keys, notification channels
└── core/                — App-level initialization
```

### Data Flow

```
MediaStore ──► MediaProvider ──► Repository ──► ViewModel ──► Fragment/Activity
                                                   ▲
                                              LiveData (observed)
```

### Playback Architecture

| Playback Type | Engine                                    | Session Support   | Background            |
| ------------- | ----------------------------------------- | ----------------- | --------------------- |
| **Video**     | Singleton `ExoPlayer` via `ExoplayerUtil` | ❌                | ❌                    |
| **Music**     | `ExoPlayer` inside `MusicPlayerService`   | ✅ `MediaSession` | ✅ Foreground Service |

---

## 🛠️ Tech Stack

| Technology              | Version   | Purpose                                    |
| ----------------------- | --------- | ------------------------------------------ |
| **Java**                | 17        | Primary language                           |
| **Gradle**              | AGP 9.1.1 | Build system with Version Catalog          |
| **Media3 ExoPlayer**    | 1.10.1    | Video & audio playback engine              |
| **Media3 UI**           | 1.10.1    | `PlayerView` for video rendering           |
| **Media3 Session**      | 1.10.1    | `MediaSessionService` for background audio |
| **Room**                | 2.8.4     | Local SQLite database (play history)       |
| **Material Components** | 1.14.0    | UI widgets & theming                       |
| **ConstraintLayout**    | 2.2.1     | Flexible layout system                     |
| **ViewBinding**         | ✅        | Type-safe view references                  |

---

## 📋 Prerequisites

- **Android Studio** Meerkat (2024.3.1) or newer
- **JDK 17**
- **Android SDK 36**
- Physical device or emulator running **Android 12+ (API 31)**

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/rashidekbal/4K-Video-Player.git
cd 4K-Video-Player
```

### 2. Open in Android Studio

- Open Android Studio → **File** → **Open** → select the project root directory
- Wait for Gradle sync to complete

### 3. Build and Run

```bash
./gradlew assembleDebug
```

Or simply click **Run ▶** in Android Studio on a connected device / emulator.

### 4. Grant Permissions

On first launch, the app will request:

- `READ_MEDIA_VIDEO` — to scan video files
- `READ_MEDIA_AUDIO` — to scan audio files

---

## 📱 App Screens

| Screen           | Description                                                                                                    |
| ---------------- | -------------------------------------------------------------------------------------------------------------- |
| **Home**         | Tabbed navigation with Recents, Music, and Video sections + mini player                                        |
| **Video Player** | Full-screen player with custom overlay: seek bar, play/pause, prev/next, back button, and auto-hiding controls |
| **Music Player** | Dedicated audio player with progress bar, seek forward/backward (10s), prev/next track, play/pause             |

---

## 📁 Project Structure

```
4Kvideoplayer/
├── app/
│   ├── build.gradle                    — App-level build config & dependencies
│   ├── src/main/
│   │   ├── AndroidManifest.xml         — Activities, service, permissions
│   │   ├── java/.../videoplayer/       — All Java source (30 files)
│   │   └── res/
│   │       ├── layout/                 — 8 XML layouts (activities, fragments, cards)
│   │       ├── drawable/               — 35 custom vector icons & assets
│   │       ├── drawable-night/         — Dark mode drawables
│   │       ├── values/                 — Colors, strings, themes, dimensions
│   │       ├── values-night/           — Dark theme overrides
│   │       ├── values-sw{240..720}dp/  — Responsive dimension resources
│   │       └── xml/                    — Backup & data extraction rules
├── build.gradle                        — Root build file
├── settings.gradle                     — Project settings
├── gradle/
│   └── libs.versions.toml              — Centralized dependency versions
└── README.md
```

---

## 🗺️ Roadmap

- [ ] **Complete Recents/History** — Wire Room database to track and display recently played media
- [ ] **Video Thumbnails** — Load video thumbnails in the list using `ContentResolver` or Glide
- [ ] **Search** — Implement search/filter across music and video libraries
- [ ] **Favorites** — Add like/favorite functionality with heart icon toggle
- [ ] **Loop / Repeat Mode** — Single track repeat and playlist loop
- [ ] **Landscape Video Layout** — Optimized layout for landscape video playback
- [ ] **Screen Rotation Lock** — Toggle rotation from within the video player
- [ ] **Equalizer** — Audio equalizer integration
- [ ] **Subtitle Support** — SRT/ASS subtitle loading and display
- [ ] **Playlist Management** — Create and manage custom playlists

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<p align="center">
  Built with ❤️ by <a href="https://github.com/rashidekbal">Rasid</a>
</p>
