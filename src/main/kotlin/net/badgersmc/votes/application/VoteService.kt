package net.badgersmc.votes.application

import net.badgersmc.nexus.i18n.LangService
import net.badgersmc.votes.domain.PlayerStats
import net.badgersmc.votes.domain.VoteParty
import net.badgersmc.votes.domain.VoteRecord
import net.badgersmc.votes.infrastructure.config.VoteConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Sound
import java.util.UUID

class VoteService(
    private val repo: VoteRepository,
    private val broadcaster: VoteBroadcaster,
    private val goldDelivery: GoldDelivery,
    private val votePartyService: VotePartyService,
    private val config: VoteConfig,
    private val lang: LangService,
) {
    private val voteSound: Sound by lazy { parseSound(config.voteSound) }
    private val allSitesSound: Sound by lazy { parseSound(config.allSitesSound) }

    private fun parseSound(name: String): Sound = try {
        Sound.valueOf(name.uppercase())
    } catch (_: IllegalArgumentException) {
        Sound.ENTITY_EXPERIENCE_ORB_PICKUP
    }

    fun processVote(playerName: String, playerUuid: UUID, serviceName: String): VoteResult {
        val stats = repo.getStats(playerUuid)
        val streak = stats.currentStreak + 1

        var gold = (config.minGold..config.maxGold).random()
        val record = VoteRecord(
            playerUuid = playerUuid,
            playerName = playerName,
            serviceName = serviceName,
            goldAwarded = gold,
        )
        repo.saveVote(record)

        val player = Bukkit.getPlayer(playerUuid)
        if (player != null) {
            // Audio cue for individual vote (tabbed-out voters)
            try { player.playSound(player.location, voteSound, 1.0f, 1.0f) } catch (_: Exception) {}

            // Check if they voted on all configured sites today (match by serviceName)
            val todaysServices = repo.getTodaysServices(playerUuid)
            val matchedSites = config.voteSites.count { it.serviceName.isNotBlank() && it.serviceName in todaysServices }
            val allSitesComplete = matchedSites >= config.voteSites.size && config.voteSites.isNotEmpty()
            if (allSitesComplete) {
                gold += config.allSitesBonusGold
                try { player.playSound(player.location, allSitesSound, 1.0f, 1.0f) } catch (_: Exception) {}
                val bonusMsg = lang.msg("voteparty.all_sites_bonus", "bonus" to config.allSitesBonusGold.toString())
                player.sendMessage(bonusMsg)
            }

            goldDelivery.deliver(playerUuid, gold)
        } else {
            repo.queueOfflineGold(playerUuid, gold)
        }

        // VoteParty: check if party just activated (AFTER saving vote, so threshold voter gets bonus)
        val partyState = votePartyService.onVote()
        if (partyState.justActivated) {
            val partyMsg = lang.msg(
                "voteparty.broadcast",
                "minutes" to config.votePartyDurationMinutes.toString(),
            )
            broadcaster.broadcastVoteParty(partyMsg)
        }

        val streakText = if (streak > 1) {
            MiniMessage.miniMessage().serialize(
                lang.msg("voteparty.streak_suffix", "streak" to streak.toString()),
            )
        } else {
            ""
        }
        val message = lang.msg(
            "voteparty.reward_message",
            "player" to playerName,
            "service" to serviceName,
            "streak_text" to streakText,
        )
        broadcaster.broadcastVote(message)

        return VoteResult(
            record = record,
            stats = stats,
            gold = gold,
            streak = streak,
            broadcastMessage = message,
        )
    }
}

data class VoteResult(
    val record: VoteRecord,
    val stats: PlayerStats,
    val gold: Int,
    val streak: Int,
    val broadcastMessage: Component,
)
