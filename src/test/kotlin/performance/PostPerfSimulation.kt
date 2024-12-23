package performance

import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.core.Simulation

class PostPerfSimulation : Simulation() {

    init {
        setUp(
            listOf(PostPerfScenario().scn().injectOpen(atOnceUsers(1)).protocols(BaseProtocol().httpProtocol()))
        )
    }
}
