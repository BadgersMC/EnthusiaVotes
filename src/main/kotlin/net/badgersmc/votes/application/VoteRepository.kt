package net.badgersmc.votes.application

import net.badgersmc.votes.domain.PlayerStats
import net.badgersmc.votes.domain.VoteRecord
import java.util.UUID

interface VoteRepository {
    fun saveVote(record: VoteRecord)
    fun getStats(uuid: UUID): PlayerStats
    fun getTotalVotes(uuid: UUID): Int
    fun getTopVoters(limit: Int): List<PlayerStats>
    fun getTotalServerVotes(): Int
}
