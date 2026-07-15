# Tech Stack — EnthusiaVotes

| Component | Version | Role |
|---|---|---|
| Kotlin | 2.1.0 | Language |
| Paper API | 1.21.11-R0.1-SNAPSHOT | Server platform |
| Nexus Core | v2.1.1 | DI, scheduler, persistence, paper-loader |
| NuVotifier API | 2.7.3 | Vote event integration (compileOnly) |
| Geyser API | 2.4.2-SNAPSHOT | Bedrock form support (compileOnly) |
| Exposed | 0.55.0 | SQLite ORM |
| SQLite JDBC | 3.45.1.0 | Embedded database |
| HikariCP | 5.1.0 | Connection pool |
| Adventure API | 4.17.0 | Text components (bundled with Paper) |
| Adventure MiniMessage | 4.17.0 | Rich text formatting |
| PlaceholderAPI | 2.11.6 | Placeholder integration (compileOnly) |
| JUnit Jupiter | 5.10.0 | Testing |
| MockK | 1.13.10 | Mocking |
| MockBukkit | v1.21:3.127.0 | Bukkit test harness |
| Konsist | 0.17.3 | Layer-rule enforcement |
| Gradle | 8.10 | Build tool |
| Shadow (GradleUp) | 8.3.5 | Fat JAR |

## Build plugins
- `kotlin("jvm")` 2.1.0
- `com.gradleup.shadow` 8.3.5

## Repositories
- Maven Central, PaperMC, extendedclip, JitPack, OpenCollab
