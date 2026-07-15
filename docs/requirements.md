# Requirements — EnthusiaVotes

## Functional

### REQ-001 — Vote event handling
**Event-driven.** WHEN the NuVotifier plugin fires a VotifierEvent, THE SYSTEM SHALL resolve the player's UUID from the provided username, record the vote in persistent storage, calculate and deliver a randomized RAW_GOLD reward, and broadcast a formatted announcement to all online players.

### REQ-002 — Random gold rewards
**Event-driven.** WHEN a vote is processed, THE SYSTEM SHALL randomly select a gold amount between the configured minimum and maximum and create a RAW_GOLD item stack with Unbreaking 1 and HIDE_ENCHANTS.

### REQ-003 — Vote streak tracking
**State-driven.** WHILE a player votes within a 24-hour window of their previous vote, THE SYSTEM SHALL increment their vote streak and apply the corresponding gold multiplier based on streak length.

### REQ-004 — Vote Party activation
**Event-driven.** WHEN the total server vote count reaches the configured threshold, THE SYSTEM SHALL activate a Vote Party that doubles all gold rewards and auto-deactivates after the configured duration.

### REQ-005 — Vote Party broadcasts
**Event-driven.** WHEN a Vote Party activates, THE SYSTEM SHALL broadcast an announcement to all players using MiniMessage gradient formatting with shadow text.

### REQ-006 — /vote command
**Event-driven.** WHEN a player executes `/vote`, THE SYSTEM SHALL display their vote statistics and a list of voting sites with clickable links using Adventure MiniMessage shadow text.

### REQ-007 — /votesites command
**Event-driven.** WHEN a player executes `/votesites`, THE SYSTEM SHALL display a menu of clickable voting site links formatted with Adventure MiniMessage shadow text.

### REQ-008 — /evadmin command
**Ubiquitous.** THE SYSTEM SHALL provide an admin command `/evadmin` for vote management including force party, stats, and reload subcommands.

### REQ-009 — Persistent storage
**Ubiquitous.** THE SYSTEM SHALL store all vote records and player statistics in a SQLite database accessible via Exposed ORM with automatic schema migration on first access.

### REQ-010 — Command registration
**Ubiquitous.** THE SYSTEM SHALL register all commands via the server's CommandMap rather than YAML-based declarations to maintain Paper 1.21+ compatibility.

## Non-functional

### REQ-011 — Layer separation
**Ubiquitous.** THE SYSTEM SHALL use a domain-app-infrastructure layer architecture where domain must not import infrastructure or platform types.

### REQ-012 — Manual dependency injection
**Ubiquitous.** THE SYSTEM SHALL wire all components manually via a central ServiceModule without framework-based reflection or annotation scanning.

### REQ-013 — MiniMessage shadow text
**Ubiquitous.** THE SYSTEM SHALL use Adventure MiniMessage serialization with shadow wrapper for all user-facing messages matching the EnthusiaMarket presentation style.
