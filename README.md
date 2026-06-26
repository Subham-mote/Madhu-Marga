<div align="center">

# 🍯 Madhu-Marga

### Digital Beekeeper's Diary — an Android app that helps beekeepers monitor hive health and harvests

[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-7F52FF.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)](https://developer.android.com/jetpack/compose)
[![Room DB](https://img.shields.io/badge/Storage-Room%20DB-92400E.svg)](https://developer.android.com/training/data-storage/room)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-amber.svg)](#)

</div>

---

## ✨ What is this?

Beekeeping is a valuable secondary income for rural farmers, but beginners often lose colonies because they can't spot the early warning signs of a failing hive. **Madhu-Marga** ("the path of honey") is a digital diary that lets a beekeeper log what they observe in each hive — and get instant, actionable advice back from a built-in rule-based advisor, no internet connection required.

It also tracks honey harvests over time and shows a Flora Calendar so beekeepers know what's blooming nearby — and when to expect a strong nectar flow.

---

## 📱 Screens

| Screen | What it does |
|---|---|
| **Home** | Dashboard with quick actions, the latest advice card, and a list of all registered hives |
| **Register Hive** | Tag a new hive with an ID and location |
| **Inspection Log** | Record queen sighting, pests, honey flow, activity level, brood pattern, temperament, temperature |
| **Harvest Tracker** | Log honey quantity per hive and see a year-over-year comparison |
| **Flora Calendar** | Month-wise guide to blooming flowers and their nectar/pollen ratings |
| **Hive Detail** | Per-hive history of inspections and harvests |

<p align="center">
  width="100%">
</p>

---

## 🧠 The Advisor — a rule-based "Decision Matrix"

Instead of calling an external AI API, Madhu-Marga ships a local **rule-based advisor** (`HiveAdvisor.kt`) that turns your latest inspection into prioritized advice — instantly, and fully offline. A few examples of what it watches for:

- **Low activity** → flags a possible intervention alert (queenlessness, robbing, pesticide exposure)
- **Queen not sighted** → suggests checking for fresh eggs before re-queening
- **Pests detected** → recommends identifying the pest and treatment options
- **High activity + strong honey flow** → suggests considering a colony split to prevent swarming
- **Extreme temperature readings** → warns about overheating or cold stress

This keeps the app instant and works even with zero network connectivity — and it's designed to be swapped for a real LLM call later if desired.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| Local storage | Room DB (hives, inspections, harvests) |
| Async | Kotlin Coroutines |
| Min SDK | API 24 (Android 7.0 Nougat) |
| Target SDK | API 34 |

---

## 📂 Project Structure

app/src/main/java/com/madhumarga/app/

├── data/

│   ├── Entities.kt          # HiveEntity, InspectionEntity, HarvestEntity

│   ├── Daos.kt              # Room DAOs

│   ├── AppDatabase.kt

│   └── HiveRepository.kt

├── domain/

│   ├── HiveAdvisor.kt       # Rule-based decision matrix

│   ├── HarvestAnalytics.kt  # Year-over-year harvest totals

│   └── FloraCalendar.kt     # Bloom data by region & month

├── ui/

│   ├── HiveViewModel.kt

│   ├── screens/             # Home, HiveRegister, InspectionLog, HarvestTracker, FloraCalendar, HiveDetail

│   ├── components/          # HoneyFlowBar, AdviceCard, SectionHeader

│   └── theme/               # Color.kt, Type.kt, Theme.kt

├── MainActivity.kt

└── MadhuMargaApp.kt

- **HiveEntity** — id, tag, location, notes
- **InspectionEntity** — queenSeen, pestsSeen, honeyFlow, activityLevel, temperatureC, notes
- **HarvestEntity** — quantityKg, date, notes

---

## 🚀 Getting Started

1. Clone the repo and open it in **Android Studio (Hedgehog or later)**
2. Let Gradle sync — Room's KSP annotation processor will generate the DB code
3. Run on an emulator or device with **API 24+**

```bash
git clone https://github.com/Subham-mote/Madhu-Marga.git
```

No API keys, no backend setup — everything runs locally on-device.

---

## 🎯 Impact

- **Sweet Revolution** — helping increase high-quality honey production for rural farmers
- **Biodiversity** — healthier hives support pollination of surrounding crops
- **Sustainable Income** — a low-cost, high-value secondary income source, made more reliable through data

---

## 🤝 Contributing

Pull requests are welcome — whether it's expanding the Flora Calendar to more regions, adding more rules to the advisor, or wiring up a real GenAI backend.

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

<div align="center">

**Built for beekeepers, by a beekeeper-curious developer 🐝**

</div>

