package net.badgersmc.votes.infrastructure.bukkit

import java.util.Locale
import java.util.UUID
import net.badgersmc.votes.application.VoteSoundPlayer
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

/** Custom pack events are intentionally resolved outside Bukkit's vanilla registry. */
class BukkitVoteSoundPlayer(private val findPlayer: (UUID) -> Player? = Bukkit::getPlayer) : VoteSoundPlayer {
    override fun play(playerId: UUID, configured: String) {
        val player = findPlayer(playerId) ?: return
        if (CUSTOM_KEY.matches(configured)) {
            player.playSound(player.location, configured, 1.0f, 1.0f)
        } else {
            val sound = try { Sound.valueOf(configured.uppercase(Locale.ROOT)) }
                catch (_: IllegalArgumentException) { Sound.ENTITY_EXPERIENCE_ORB_PICKUP }
            player.playSound(player.location, sound, 1.0f, 1.0f)
        }
    }
    private companion object {
        val CUSTOM_KEY = Regex("[a-z0-9._-]+:[a-z0-9/._-]+")
    }
}
