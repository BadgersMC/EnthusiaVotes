package net.badgersmc.votes.application

import net.badgersmc.nexus.i18n.LangService
import net.kyori.adventure.text.Component
import java.util.UUID

class RewardService(
    private val voteRepository: VoteRepository,
    private val votePartyService: VotePartyService,
    private val lang: LangService,
) {
    fun getMiningMultiplier(uuid: UUID): Double {
        val stats = voteRepository.getStats(uuid)
        val streak = stats.currentStreak
        val streakMult = streakMultiplier(streak)
        val partyMult = votePartyService.getCurrentMultiplier()
        return streakMult * partyMult
    }

    fun streakMultiplier(streak: Int): Double = when {
        streak >= 30 -> 3.0
        streak >= 7 -> 2.0
        streak >= 3 -> 1.5
        else -> 1.0
    }

    fun buildVoteMessage(
        playerName: String,
        multiplier: Double,
        streak: Int,
        serviceName: String,
    ): Component {
        val streakText = if (streak > 1)
            lang.msg("voteparty.streak_suffix", "streak" to streak.toString())
        else Component.empty()
        return lang.msg(
            "voteparty.reward_message",
            "player" to playerName,
            "service" to serviceName,
            "multiplier" to multiplier.toString(),
            "streak_text" to streakText,
        )
    }
}