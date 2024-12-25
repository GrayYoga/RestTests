package performance

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.Simulation


class PostPerfSimulation : Simulation() {

    init {
        setUp(
            listOf(
                PostPerfScenario
                    .scn()
                    .injectOpen(
                        CoreDsl.rampUsersPerSec(0.0)
                            .to(1000.0)
                            .during(1 * 60L),
                    )
                    .protocols(BaseProtocol().httpProtocol())
            )
        )
    }
}
