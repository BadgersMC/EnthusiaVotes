package net.badgersmc.votes.infrastructure.bukkit

import net.badgersmc.nexus.i18n.LangService
import net.badgersmc.votes.application.VoteTopCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class VoteTopBukkitCommand(
    private val voteTopCommand: VoteTopCommand,
    private val lang: LangService,
    private val plugin: JavaPlugin,
) : Command("votetop") {
    init {
        permission = "enthusiavotes.vote"
    }

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(lang.msg("error.players_only"))
            return true
        }

        val name = sender.name
        val uuid = sender.uniqueId
        plugin.server.scheduler.runTaskAsynchronously(plugin, Runnable {
            val message = voteTopCommand.execute(name, uuid)
            sender.sendMessage(message)
        })
        return true
    }
}
