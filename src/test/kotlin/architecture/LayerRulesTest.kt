package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Assertions.assertTrue as assertNotEmpty
import org.junit.jupiter.api.Test

class LayerRulesTest {

    private fun belongsTo(name: String, packageName: String): Boolean =
        name == packageName || name.startsWith("$packageName.")

    @Test
    fun `domain layer must not depend on infrastructure`() {
        val files = Konsist.scopeFromProduction().files.withPackage("net.badgersmc.votes.domain..")
        assertNotEmpty(files.isNotEmpty(), "Domain scope must contain production files")
        files.assertTrue { file ->
            file.imports.none { imported ->
                belongsTo(imported.name, "net.badgersmc.votes.infrastructure") ||
                    belongsTo(imported.name, "org.bukkit") ||
                    belongsTo(imported.name, "io.papermc")
            }
        }
    }

    @Test
    fun `application layer must not import infrastructure packages`() {
        val files = Konsist.scopeFromProduction().files.withPackage("net.badgersmc.votes.application..")
        assertNotEmpty(files.isNotEmpty(), "Application scope must contain production files")
        files.assertTrue { file ->
            file.imports.none { belongsTo(it.name, "net.badgersmc.votes.infrastructure") }
        }
    }

    @Test
    fun `infrastructure layer may import domain and application`() {
        val files = Konsist.scopeFromProduction().files.withPackage("net.badgersmc.votes.infrastructure..")
        assertNotEmpty(files.isNotEmpty(), "Infrastructure scope must contain production files")
        files.assertTrue { file ->
            file.imports.filter { belongsTo(it.name, "net.badgersmc.votes") }.all {
                belongsTo(it.name, "net.badgersmc.votes.domain") ||
                    belongsTo(it.name, "net.badgersmc.votes.application") ||
                    belongsTo(it.name, "net.badgersmc.votes.infrastructure")
            }
        }
    }
}
