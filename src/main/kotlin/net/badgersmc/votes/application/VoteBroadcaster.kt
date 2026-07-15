package net.badgersmc.votes.application

import net.kyori.adventure.text.Component

interface VoteBroadcaster {
    fun broadcastVote(message: Component)
    fun broadcastVoteParty(message: Component)
}
