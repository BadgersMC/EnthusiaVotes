package net.badgersmc.votes.infrastructure.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import java.io.File

class DatabaseFactory(
    private val dataFolder: File,
    private val fileName: String,
) {
    val database: Database by lazy {
        dataFolder.mkdirs()
        val url = "jdbc:sqlite:${dataFolder.absolutePath}/$fileName"
        Database.connect(url, "org.sqlite.JDBC")
    }

    fun close() {
        // SQLite auto-closes on JVM exit
    }
}

object VoteTable : Table("votes") {
    val id = long("id").autoIncrement()
    val playerUuid = text("player_uuid")
    val playerName = text("player_name")
    val serviceName = text("service_name")
    val timestamp = long("timestamp")
    val goldAwarded = integer("gold_awarded")

    override val primaryKey = PrimaryKey(id)
}

object PlayerStatsTable : Table("player_stats") {
    val playerUuid = text("player_uuid")
    val totalVotes = integer("total_votes").default(0)
    val currentStreak = integer("current_streak").default(0)
    val bestStreak = integer("best_streak").default(0)
    val lastVoteAt = long("last_vote_at").nullable()

    override val primaryKey = PrimaryKey(playerUuid)
}

object Migrations {
    fun run() {
        DatabaseFactory::class.java // forces lazy init check
        // Tables are created lazily via application code
    }
}
