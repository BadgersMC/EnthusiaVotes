package net.badgersmc.votes.application
import io.mockk.*
import net.badgersmc.votes.infrastructure.config.VoteConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
class VotePartySchedulingTest {
 @Test fun `threshold and duration preserve persistence and deactivation timing`() {
  var delay = -1L
  var callback: (() -> Unit)? = null
  val repo = mockk<VoteRepository>(relaxed = true)
  val service = VotePartyService(VoteConfig(votePartyThreshold = 1, votePartyDurationMinutes = 5),
   { ticks, action -> delay = ticks; callback = action; Any() }, repo)
  assertTrue(service.onVote().justActivated)
  assertTrue(service.isPartyActive())
  assertEquals(6000L,delay)
  verify(exactly = 1) { repo.savePartyState(match { it.active && it.currentVotes == 0 }) }
  callback!!()
  assertFalse(service.isPartyActive())
  verify(exactly = 1) { repo.savePartyState(match { !it.active && it.currentVotes == 0 }) }
 }
}
