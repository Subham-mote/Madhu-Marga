# Madhu-Marga — Digital Beekeeper's Diary

An Android app for hobbyist and small-holding beekeepers in India to log hive
observations, get rule-based interventions, track honey harvests year-over-year,
and reference a regional flora calendar.

Built with **Kotlin · Jetpack Compose · Room · Material 3**. Targets Android 7.0+
(API 24). No external API keys required to run.

## What's in the box

| Feature                | Where                                                   |
|------------------------|---------------------------------------------------------|
| Hive Register          | "New Hive" button on Home → tag + location              |
| Inspection Log         | Home → Inspection (queen, pests, flow, activity, temp)  |
| Harvest Tracker        | Home → Harvest (per-hive, with year-over-year chart)    |
| Flora Calendar         | Home → Flora (monthly nectar/pollen guide for India)    |
| Decision Matrix advice | Home + Hive Detail (driven by latest inspection)        |
| Intervention Alerts    | Red banner fires when activity is logged as "Low"       |
| Honey Flow progress    | Progress bar on Hive Detail                             |
| Local persistence      | Room DB (`madhu_marga.db`)                              |

The "AI assistant" is currently a deterministic rule-based **Decision Matrix**
in `domain/HiveAdvisor.kt`. The interface is small enough to swap in a real
LLM (Gemini, Claude, etc.) when you're ready — see *Adding a real GenAI* below.

## Opening in Android Studio

1. Launch **Android Studio Hedgehog or newer** (Iguana/Jellyfish are also fine).
2. *File → Open* and select this folder: `MadhuMarga/`.
3. Wait for Gradle sync. The first sync downloads:
   - Gradle 8.4
   - Android Gradle Plugin 8.2.2
   - Compose BOM 2024.02.00, Room 2.6.1, Navigation 2.7.7
   - KSP 1.9.22-1.0.17
4. When prompted, install any missing SDK platform (API 34) and build tools.
5. Plug in an Android device with USB debugging enabled, **or** create an
   emulator (Tools → Device Manager → Create Device → Pixel 6 → API 34).
6. Press the green **Run** button (▶) targeting the `app` configuration.

## Running from the command line (optional)

After Android Studio has generated `gradlew`:

```
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

If `gradlew` isn't generated yet, open the project in Android Studio once and
Gradle will create the wrapper script.

## Adding a real GenAI (future, when you have an API key)

The advisor is wrapped behind a single function:

```kotlin
HiveAdvisor.advise(inspection): List<Advice>
```

To swap in Gemini or Claude:

1. Add the SDK dependency in `app/build.gradle.kts`.
2. Add your API key to `local.properties` (never commit it):
   ```
   GEMINI_API_KEY=...
   ```
3. Surface it via `buildConfigField` and call from a new
   `GenAiHiveAdvisor` that returns the same `List<Advice>` shape.
4. Inject it where `HiveAdvisor.advise(...)` is currently called
   (`HomeScreen.kt`, `HiveDetailScreen.kt`).

I'll wire this up for you whenever you have a key — just paste it and tell me
which model you want to use.

## Project layout

```
app/src/main/java/com/madhumarga/app/
├── MainActivity.kt              ← Compose entry + navigation graph
├── MadhuMargaApp.kt             ← Application + DI wiring
├── data/                        ← Room: entities, DAOs, DB, repository
├── domain/                      ← HiveAdvisor (decision matrix), FloraCalendar,
│                                  HarvestAnalytics (year-over-year)
└── ui/
    ├── HiveViewModel.kt
    ├── theme/                   ← Honey/yellow/earthy Material 3 theme
    ├── components/              ← HoneyFlowBar, AdviceCard, SectionHeader
    └── screens/                 ← Home, HiveRegister, InspectionLog,
                                    HarvestTracker, FloraCalendar, HiveDetail
```

## Success criteria from the brief

- [x] **Intervention Alert** on "Low Activity" → red `AlertSeverity.CRITICAL` card.
- [x] **Harvest year-over-year comparison** → bars on `HarvestTrackerScreen`.
- [x] **Honey/Yellow tone UI** → see `ui/theme/Color.kt`.
- [x] **Room DB for hive-wise performance history** → `data/AppDatabase.kt`.
- [x] **Honey Flow progress bar** → `ui/components/HoneyFlowBar.kt`.
- [x] **Decision Matrix logic** → `domain/HiveAdvisor.kt`.
