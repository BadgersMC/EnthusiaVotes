package net.badgersmc.votes.application
import java.util.UUID
fun interface VoteSoundPlayer {
    fun play(playerId: UUID, configured: String)
}
