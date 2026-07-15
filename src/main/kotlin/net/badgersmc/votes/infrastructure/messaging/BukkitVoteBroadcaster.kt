package net.badgersmc.votes.infrastructure.messaging

import net.badgersmc.votes.application.VoteBroadcaster
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class BukkitVoteBroadcaster : VoteBroadcaster {
    override fun broadcastVote(message: Component) {
        Bukkit.getServer().sendMessage(message)
    }

    override fun broadcastVoteParty(message: Component) {
        Bukkit.getServer().sendMessage(message)
    }
}
