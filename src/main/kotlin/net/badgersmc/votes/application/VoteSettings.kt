package net.badgersmc.votes.application

/** Read-only settings consumed by vote use cases; parsing and storage settings stay in infrastructure. */
interface VoteSettings {
    val minGold: Int
    val maxGold: Int
    val votePartyThreshold: Int
    val votePartyDurationMinutes: Int
    val voteSound: String
    val allSitesSound: String
    val allSitesBonusGold: Int
    val allSitesBonusMultiplier: Double
    val voteSites: List<VoteSiteInfo>
 }
interface VoteSiteInfo {
    val name: String
    val url: String
    val serviceName: String
}
