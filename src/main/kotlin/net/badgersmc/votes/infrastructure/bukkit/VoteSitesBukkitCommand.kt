package net.badgersmc.votes.infrastructure.bukkit

import net.badgersmc.votes.application.VoteSitesCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class VoteSitesBukkitCommand(
    private val voteSitesCommand: VoteSitesCommand,
) : Command("votesites") {
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        // TODO: implement
        return true
    }
}
