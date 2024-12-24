package performance

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.OpenInjectionStep
import io.gatling.javaapi.core.Simulation


class PostPerfSimulation : Simulation() {

    init {
        setUp(
            listOf(
                PostPerfScenario
                    .scn()
                    .injectOpen(
//                        OpenInjectionStep.atOnceUsers(1)
                        CoreDsl.rampUsersPerSec(0.0)
                            .to(1000.0)
                            .during(1 * 60L),
                    )
                    .protocols(BaseProtocol().httpProtocol())
            )
        )
    }
}
