package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class LayerRulesTest {

    @Test
    fun `domain layer must not depend on infrastructure`() {
        Konsist
            .scopeFromProduction("net.badgersmc.votes.domain..")
            .assertTrue { it.resideInPackage("net.badgersmc.votes.domain..") }
    }

    @Test
    fun `application layer must not import infrastructure packages`() {
        Konsist
            .scopeFromProduction("net.badgersmc.votes.application..")
            .assertTrue { !it.hasImport("net.badgersmc.votes.infrastructure..") }
    }

    @Test
    fun `infrastructure layer may import domain and application`() {
        Konsist
            .scopeFromProduction("net.badgersmc.votes.infrastructure..")
            .assertTrue { it.resideInPackage("net.badgersmc.votes..") }
    }
}
