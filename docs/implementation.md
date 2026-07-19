# Implementation — EnthusiaVotes

## Layer Dependency Rules

```
domain ← application ← infrastructure
```

- **domain** — Pure Kotlin data classes and interfaces. No platform imports.
- **application** — Use cases, services, repository interfaces. Depends on domain only.
- **infrastructure** — Bukkit adapters, persistence, config, DI, messaging. Depends on domain + application.

## Forbidden Domain Annotations

```yaml
forbidden: []
```

## Module map

| Layer | Package | Contents |
|---|---|---|
| domain | `net.badgersmc.votes.domain` | `VoteRecord`, `PlayerStats`, `VoteParty`, `VotePartyState` |
| application | `net.badgersmc.votes.application` | `VoteService`, `VotePartyService`, `VoteCommand`, `VoteSitesCommand`, `EVAdminCommand`, `VoteTopCommand`, `VoteRepository`, `VoteBroadcaster`, `GoldDelivery`, `VotePartySpeaker` |
| infrastructure | `net.badgersmc.votes.infrastructure.bukkit` | `EnthusiaVotesPlugin`, `VotifierVoteListener`, `VoteBukkitCommand`, `VoteSitesBukkitCommand`, `EVAdminBukkitCommand`, `VoteTopBukkitCommand`, `VoteItemFactory`, `BukkitGoldDelivery`, `ProxiedDeliveryService`, `OfflineVoteLoginListener`, `VoteReminder` |
| infrastructure | `net.badgersmc.votes.infrastructure.config` | `VoteConfig`, `VoteSite`, `StorageConfig`, `MariaDbConfig` |
| infrastructure | `net.badgersmc.votes.infrastructure.di` | `ServiceModule`, `VoteScheduler` |
| infrastructure | `net.badgersmc.votes.infrastructure.persistence` | `DatabaseFactory`, `LocalDatabaseFactory`, `RemoteDatabaseFactory`, `VoteTable`, `PlayerStatsTable`, `OfflineVoteTable`, `VotePartyTable`, `SqliteVoteRepository`, `Migrations` |
| infrastructure | `net.badgersmc.votes.infrastructure.messaging` | `BukkitVoteBroadcaster` |
| infrastructure | `net.badgersmc.votes.infrastructure.form` | `BedrockVoteForm` |
| infrastructure | `net.badgersmc.votes.infrastructure.papi` | `EnthusiaVotesExpansion` |
| infrastructure | `net.badgersmc.votes.infrastructure.i18n` | `EnthusiaVotesLang` |
| resources | `src/main/resources/lang` | `en_US.yml` — all user-facing messages |
| loader | `net.badgersmc.votes.loader` | `EnthusiaVotesLoader` |

## Command registration

All commands use `server.commandMap.register(name, command)` — never `getCommand()` or `paper-plugin.yml` command stanzas. This avoids the `UnsupportedOperationException` on Paper 1.21+.

| Command | Permission | Notes |
|---|---|---|
| `vote` | `enthusiavotes.vote` (default) | Stats + vote links; routes Bedrock to Cumulus form |
| `votesites` | `enthusiavotes.vote` (default) | Vote site menu |
| `votetop` | `enthusiavotes.vote` (default) | Top 10 leaderboard; async DB query |
| `evadmin` | `enthusiavotes.admin` | forceparty, stats, party |

## Database

Supported backends: **SQLite** (local) and **MariaDB** (networked). Backend selected via `config.yml → storage.backend`.

Tables (auto-migrated on startup via `Migrations.run()`):
- `votes` — vote history (id, player_uuid, player_name, service_name, timestamp, gold_awarded)
- `player_stats` — per-player aggregates (player_uuid PK, total_votes, current_streak, best_streak, last_vote_at)
- `offline_votes` — queued gold for offline players (player_uuid PK, gold, created_at)
- `vote_party` — persisted party state (active, current_votes, threshold, started_at)

## Velocity plugin messaging

Channels registered via `ProxiedDeliveryService`:
- `enthusiavotes:deliver` — gold delivery to players on other servers
- `enthusiavotes:voteparty` — party_start/party_end broadcasts

Payload format: JSON (`{"type":"gold","playerUuid":"...","gold":5}`). No-op when no proxy present.

## DI pattern

Manual constructor injection via `ServiceModule`. Lazy initialization with `by lazy` to avoid circular references. Every component receives its dependencies as constructor parameters.

Key services:
- `LangService` — Nexus i18n, resolves `en_US.yml` via `@LangFile` marker
- `ProxiedDeliveryService` — wraps `BukkitGoldDelivery`, implements `GoldDelivery` + `VotePartySpeaker` + `PluginMessageListener`
- `VoteReminder` — `Runnable`, 6000-tick interval via `runTaskTimer`
- `VotePartyService` — `AtomicInteger` counter, persists state on every change
