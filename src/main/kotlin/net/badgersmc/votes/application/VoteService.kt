package net.badgersmc.votes.application

import net.badgersmc.nexus.i18n.LangService
import net.badgersmc.votes.domain.PlayerStats
import net.badgersmc.votes.domain.VoteParty
import net.badgersmc.votes.domain.VoteRecord
import net.badgersmc.votes.infrastructure.config.VoteConfig
import net.kyori.adventure.text.Component
import java.util.UUID

class VoteService(
    private val repo: VoteRepository,
    private val rewardService: RewardService,
    private val broadcaster: VoteBroadcaster,
    private val votePartyService: VotePartyService,
    private val config: VoteConfig,
    private val lang: LangService,
) {
    fun processVote(playerName: String, playerUuid: UUID, serviceName: String): VoteResult {
        val stats = repo.getStats(playerUuid)
        val streak = stats.currentStreak + 1

        val multiplier = rewardService.streakMultiplier(streak) * votePartyService.getCurrentMultiplier()
        val record = VoteRecord(
            playerUuid = playerUuid,
            playerName = playerName,
            serviceName = serviceName,
        )
        repo.saveVote(record)

        // VoteParty: check if party just activated
        val partyState = votePartyService.onVote()
        if (partyState.justActivated) {
            val partyMsg = lang.msg(
                "voteparty.broadcast",
                "minutes" to config.votePartyDurationMinutes.toString(),
            )
            broadcaster.broadcastVoteParty(partyMsg)
        }

        val message = rewardService.buildVoteMessage(playerName, multiplier, streak, serviceName)
        broadcaster.broadcastVote(message)

        return VoteResult(
            record = record,
            stats = stats,
            multiplier = multiplier,
            streak = streak,
            broadcastMessage = message,
        )
    }
}

data class VoteResult(
    val record: VoteRecord,
    val stats: PlayerStats,
    val multiplier: Double,
    val streak: Int,
    val broadcastMessage: Component,
)