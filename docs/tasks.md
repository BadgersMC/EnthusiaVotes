# Tasks — EnthusiaVotes

## Completed

### TASK-001 — VoteItemFactory and gold delivery
**Tag:** INFRA
**References:** REQ-002, REQ-009
**Evidence:** ✅
- [x] `VoteItemFactory.kt` — `rawGold()` with Unbreaking 1 + HIDE_ENCHANTS
- [x] `BukkitGoldDelivery.kt` — delivers to inventory, drops excess on ground
- [x] Wired via `GoldDelivery` interface in `ServiceModule`

### TASK-002 — Votifier event listener with UUID resolution
**Tag:** INFRA
**References:** REQ-001, REQ-010
**Evidence:** ✅
- [x] `VotifierVoteListener.kt` — handles `VotifierEvent`, delegates to `VoteService`
- [x] UUID resolved via `Bukkit.getOfflinePlayer(playerName).uniqueId`
- [x] Registered in `EnthusiaVotesPlugin.onEnable` via `server.pluginManager.registerEvents`

### TASK-003 — Vote processing service
**Tag:** TDD
**References:** REQ-001, REQ-002, REQ-003, REQ-009
**Evidence:** ✅
- [x] `VoteService.processVote()` — saves to DB, delivers gold, computes streak, broadcasts
- [x] Triggers `VotePartyService.onVote()` for party tracking
- [x] Broadcasts vote announcement via `VoteBroadcaster`

### TASK-004 — Vote rewards
**Tag:** TDD
**References:** REQ-002, REQ-003, REQ-004
**Evidence:** ✅
- [x] Randomized gold rewards
- [x] MiniMessage vote broadcasts

### TASK-005 — VoteParty state machine
**Tag:** TDD
**References:** REQ-004, REQ-005
**Evidence:** ✅
- [x] `VotePartyService` — `AtomicInteger` vote counter, threshold-based activation (default 100)
- [x] Auto-deactivation via Bukkit scheduler after configurable duration (default 5m)
- [x] Broadcast on activation via gradient MiniMessage shadow text

### TASK-006 — /vote command
**Tag:** INFRA
**References:** REQ-006, REQ-013
**Evidence:** ✅
- [x] `VoteCommand.execute()` — shows total votes, current streak, best streak
- [x] Lists vote sites with clickable `<click:open_url>` links
- [x] MiniMessage shadow text throughout
- [x] `VoteBukkitCommand` — registered via `server.commandMap.register`

### TASK-007 — /votesites command
**Tag:** INFRA
**References:** REQ-007, REQ-013
**Evidence:** ✅
- [x] `VoteSitesCommand.execute()` — clickable vote site menu
- [x] MiniMessage shadow text with `<click:open_url>` links
- [x] `VoteSitesBukkitCommand` — registered via `server.commandMap.register`

### TASK-008 — Bedrock form support for /vote
**Tag:** INFRA
**References:** REQ-006
**Evidence:** ✅
- [x] `BedrockVoteForm` — Cumulus `SimpleForm` with vote stats + site buttons
- [x] `isBedrockPlayer()` — Floodgate API check with graceful fallback
- [x] `VoteBukkitCommand` routes Bedrock to form, Java to chat
- [x] Button click sends plain text URL (Bedrock can't open clickable links)

### TASK-009 — PlaceholderAPI integration
**Tag:** INFRA
**References:** none
**Evidence:** ✅
- [x] `EnthusiaVotesExpansion` — extends `PlaceholderExpansion`
- [x] Player placeholders: `%enthusiavotes_total%`, `%enthusiavotes_streak%`, `%enthusiavotes_best_streak%`
- [x] Party placeholders: `%enthusiavotes_party_active%`, `%enthusiavotes_party_votes%`, `%enthusiavotes_party_remaining%`
- [x] Auto-registration in `onEnable` if PlaceholderAPI present

### TASK-010 — /evadmin command implementation
**Tag:** INFRA
**References:** REQ-008
**Evidence:** ✅
- [x] Subcommands: `forceparty`, `stats` (top voters), `party` (status)
- [x] Permission: `enthusiavotes.admin`
- [x] `EVAdminBukkitCommand` registered via `server.commandMap.register`

### TASK-011 — PlayerStats table integration in /vote
**Tag:** TDD
**References:** REQ-006, REQ-009
**Evidence:** ✅
- [x] `/vote` pulls real stats from `SqliteVoteRepository.getStats()`
- [x] `PlayerStatsTable` Exposed table with PK and streak columns
- [x] Streak computation with 36-hour window in `computeNewStreak()`

### TASK-012 — MariaDB support
**Tag:** INFRA
**References:** REQ-009
**Evidence:** ✅
- [x] `DatabaseFactory` interface → `LocalDatabaseFactory` (SQLite) + `RemoteDatabaseFactory` (MariaDB)
- [x] HikariCP connection pool: 10 max, 2 min idle, 6min max lifetime
- [x] Config: `storage.backend` (sqlite|mariadb) + `storage.mariadb.*`
- [x] Backward compatible — `storage.backend: sqlite` behaves identically

### TASK-013 — Velocity plugin messaging
**Tag:** INFRA
**References:** REQ-001, REQ-002
**Evidence:** ✅
- [x] `ProxiedDeliveryService` — channels `enthusiavotes:deliver` and `enthusiavotes:voteparty`
- [x] Cross-server gold delivery when player is on another server
- [x] `VotePartySpeaker` interface — `onPartyActivated()` / `onPartyDeactivated()`
- [x] No-op when no Velocity/Bungee proxy present

### TASK-014 — i18n language file
**Tag:** INFRA
**References:** REQ-006, REQ-007, REQ-008
**Evidence:** ✅
- [x] `lang/en_US.yml` — all user-facing messages with `<param>` placeholders
- [x] `EnthusiaVotesLang.kt` — `@LangFile` marker class (Nexus LangService pattern)
- [x] All 7 user-facing files refactored from `mm.deserialize()` → `lang.msg()`
- [x] Bedrock forms get plain-text from `LegacyComponentSerializer`

### TASK-015 — VoteParty persistence
**Tag:** TDD
**References:** REQ-004, REQ-005
**Evidence:** ✅
- [x] `VotePartyTable` — Exposed table: active, current_votes, threshold, started_at
- [x] `VotePartyService.loadFrom()` — restores party state from DB
- [x] `resumeGiveawaysOnStartup()` — elapsed-time-aware rescheduling
- [x] `persist()` called on every state change (activate, deactivate, onVote)
- [x] `AtomicInteger` for thread-safe vote counting

### TASK-016 — Offline vote queue
**Tag:** INFRA
**References:** REQ-001, REQ-002
**Evidence:** ✅
- [x] `OfflineVoteTable` — Exposed table: player_uuid (PK), gold, created_at
- [x] `queueOfflineGold()` — upserts gold for offline players
- [x] `OfflineVoteLoginListener` — delivers queued gold via `GoldDelivery` on `PlayerJoinEvent`
- [x] `VoteService.processVote()` checks `Bukkit.getPlayer()` before queueing

### TASK-017 — /vote top leaderboard
**Tag:** INFRA
**References:** REQ-006
**Evidence:** ✅
- [x] `VoteTopCommand.execute()` — top 10 voters from `getTopVoters()`
- [x] `VoteTopBukkitCommand` — permission `enthusiavotes.vote`, async DB query
- [x] `runTaskAsynchronously` to avoid main-thread blocking
- [x] `votetop.header` and `votetop.entry` in `en_US.yml`

### TASK-018 — Vote reminders + auto-release
**Tag:** INFRA
**References:** none
**Evidence:** ✅
- [x] `VoteReminder` — 5-minute repeating broadcast via `runTaskTimer`
- [x] `vote.reminder` key in `en_US.yml`
- [x] `.github/workflows/release.yml` — tag-push trigger (`v*`), Java 21, shadowJar
- [x] Code injection fixed: `RELEASE_VERSION` env var instead of template expansion

## Pending

*(all tasks complete)*
