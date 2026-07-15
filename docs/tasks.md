# Tasks — EnthusiaVotes

## Completed

### TASK-001 — VoteItemFactory and gold delivery
**Tag:** INFRA
**References:** REQ-002, REQ-009
**Evidence:**
- [ ] VoteItemFactory.kt creates RAW_GOLD with Unbreaking 1 + HIDE_ENCHANTS
- [ ] BukkitGoldDelivery delivers gold to players, drops excess on ground

### TASK-002 — Votifier event listener with UUID resolution
**Tag:** INFRA
**References:** REQ-001, REQ-010
**Evidence:**
- [ ] VotifierVoteListener handles VotifierEvent
- [ ] UUID resolved via Bukkit.getOfflinePlayer()

### TASK-003 — Vote processing service
**Tag:** TDD
**References:** REQ-001, REQ-002, REQ-003, REQ-009
**Evidence:**
- [ ] VoteService.processVote() creates vote record, saves to DB, delivers gold, broadcasts

### TASK-004 — Reward service with streak multipliers
**Tag:** TDD
**References:** REQ-002, REQ-003, REQ-004
**Evidence:**
- [ ] RewardService.calculateGold() randomizes gold with streak + party multipliers
- [ ] RewardService.buildVoteMessage() produces MiniMessage shadow text

### TASK-005 — VoteParty state machine
**Tag:** TDD
**References:** REQ-004, REQ-005
**Evidence:**
- [ ] VotePartyService tracks vote count and activates at threshold
- [ ] Party deactivates automatically after configured duration
- [ ] Party activation broadcast uses gradient MiniMessage

### TASK-006 — /vote command
**Tag:** INFRA
**References:** REQ-006, REQ-013
**Evidence:**
- [ ] Displays player stats (total votes, streak, best streak)
- [ ] Shows clickable vote site links
- [ ] Uses MiniMessage shadow text

### TASK-007 — /votesites command
**Tag:** INFRA
**References:** REQ-007, REQ-013
**Evidence:**
- [ ] Displays clickable vote site menu
- [ ] Uses MiniMessage shadow text

## Pending

### TASK-008 — Bedrock form support for /vote
**Tag:** INFRA
**References:** REQ-006
**Evidence:**
- [ ] Bedrock players see a Geyser form instead of chat output

### TASK-009 — PlaceholderAPI integration
**Tag:** INFRA
**References:** none
**Evidence:**
- [ ] PlaceholderAPI expansion registered
- [ ] Placeholders: %enthusiavotes_streak%, %enthusiavotes_total%, %enthusiavotes_party_active%

### TASK-010 — /evadmin command implementation
**Tag:** INFRA
**References:** REQ-008
**Evidence:**
- [ ] Admin subcommands: force party, stats, reload
- [ ] Permission: enthusiavotes.admin

### TASK-011 — PlayerStats table integration in /vote
**Tag:** TDD
**References:** REQ-006, REQ-009
**Evidence:**
- [ ] /vote pulls real stats from PlayerStats table (currently uses stub data)
