package net.badgersmc.votes.application

import net.badgersmc.nexus.i18n.LangService
import net.badgersmc.votes.infrastructure.config.VoteConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import java.util.UUID

class VoteSitesCommand(
    private val voteRepository: VoteRepository,
    private val voteConfig: VoteConfig,
    private val lang: LangService,
) {
    fun execute(playerUuid: UUID): Component {
        val todaysServices = voteRepository.getTodaysServices(playerUuid)
        val lines = mutableListOf(
            lang.msg("votesites.header"),
        )

        for (site in voteConfig.voteSites) {
            val voted = site.serviceName.isNotBlank() && site.serviceName in todaysServices
            if (voted) {
                lines.add(lang.msg("votesites.site_entry_voted", "name" to site.name))
            } else {
                lines.add(lang.msg("votesites.site_entry_unvoted", "name" to site.name, "url" to site.url))
            }
        }

        return Component.join(JoinConfiguration.separator(Component.newline()), lines)
    }
}
