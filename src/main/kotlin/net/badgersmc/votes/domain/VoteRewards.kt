package net.badgersmc.votes.domain

data class VoteReward(
    val minGold: Int,
    val maxGold: Int,
    val streakMultiplier: Double = 1.0,
)

data class StreakReward(
    val day: Int,
    val goldMultiplier: Double,
    val broadcastSpecial: Boolean = false,
)
