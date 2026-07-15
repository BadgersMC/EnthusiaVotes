package net.badgersmc.votes.infrastructure.bukkit

import net.badgersmc.votes.application.EVAdminCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class EVAdminBukkitCommand(
    private val evAdminCommand: EVAdminCommand,
) : Command("evadmin") {
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        // TODO: implement
        return true
    }
}
