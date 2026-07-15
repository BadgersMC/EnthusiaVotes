package net.badgersmc.votes.infrastructure.bukkit

import net.badgersmc.votes.application.VoteCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class VoteBukkitCommand(
    private val voteCommand: VoteCommand,
) : Command("vote") {
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        // TODO: implement
        return true
    }
}
