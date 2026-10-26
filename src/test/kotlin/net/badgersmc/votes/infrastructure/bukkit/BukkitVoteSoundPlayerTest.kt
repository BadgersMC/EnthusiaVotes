package net.badgersmc.votes.infrastructure.bukkit
import io.mockk.*
import org.bukkit.entity.Player
import org.junit.jupiter.api.Test
import java.util.UUID
class BukkitVoteSoundPlayerTest {
 @Test fun `custom event is delivered once without registry fallback`() {
  val player = mockk<Player>(relaxed = true)
  val id = UUID.randomUUID()
  BukkitVoteSoundPlayer { if (it == id) player else null }.play(id, "enthusia_theme:ui.reward")
  verify(exactly = 1) { player.playSound(player.location, "enthusia_theme:ui.reward", 1.0f, 1.0f) }
 }
 @Test fun `offline recipient is ignored`() {
  BukkitVoteSoundPlayer { null }.play(UUID.randomUUID(), "enthusia_theme:ui.reward")
 }
}
