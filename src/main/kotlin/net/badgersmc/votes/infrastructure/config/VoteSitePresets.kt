package net.badgersmc.votes.infrastructure.config

/**
 * Maps vote site domains to Votifier service names.
 * Sourced from BenCodez/VotingPlugin-Presets.
 * When a VoteSite config has no explicit service-name, the loader resolves it from the URL.
 */
object VoteSitePresets {
    private val domainToService: Map<String, String> = mapOf(
        "planetminecraft.com" to "PlanetMinecraft.com",
        "minecraftservers.org" to "MinecraftServers.org",
        "minecraft-mp.com" to "Minecraft-MP.com",
        "minecraft-server.net" to "Minecraft-Server.net",
        "minecraft-server-list.com" to "MCSL",
        "minecraft.buzz" to "Minecraft.Buzz",
        "minecraftservers.blog" to "minecraftservers.blog",
        "minecraft-menu.net" to "Minecraft Menu",
        "minecraftbestservers.com" to "Minecraft Server List",
        "mc-servers.com" to "MC-Servers.com",
        "bestservers.com" to "BestServers.com",
        "best-minecraft-servers.co" to "Best Minecraft Servers",
        "crafty.gg" to "crafty.gg",
        "topg.org" to "TopG",
        "blockatlas.net" to "blockatlas",
        "curseforge.com" to "curseforge",
        "liste-serveurs.fr" to "Liste-Serveurs",
        "mc-lists.org" to "MC-Lists",
        "minestatus.net" to "minestatus.net",
        "play-minecraft-servers.com" to "play-minecraft-servers.com",
        "servers-minecraft.net" to "Servers-Minecraft",
        "topmcservers.com" to "TOPMCSERVERS",
        "topminecraftservers.org" to "TopMinecraftServers",
    )

    /**
     * Returns the Votifier service name for a vote site URL.
     * If the config provides explicit service-name, that takes priority.
     * Otherwise this resolves from the URL's hostname.
     */
    fun resolve(url: String): String {
        val host = try {
            java.net.URI(url).host?.lowercase()?.removePrefix("www.") ?: return ""
        } catch (_: Exception) { return "" }
        domainToService[host]?.let { return it }
        for ((domain, svc) in domainToService) {
            if (host.contains(domain)) return svc
        }
        return ""
    }
}
