package net.badgersmc.votes.infrastructure.config

data class VoteConfig(
    val minGold: Int = 1,
    val maxGold: Int = 10,
    val votePartyThreshold: Int = 100,
    val votePartyDurationMinutes: Int = 5,
    val enabledServices: List<String> = emptyList(),
)
