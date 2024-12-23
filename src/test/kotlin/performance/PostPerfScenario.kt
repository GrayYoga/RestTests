package performance

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl
import utils.TestData

class PostPerfScenario {
    fun scn() = CoreDsl.scenario("performance.PostPerfSimulation")
        .exec(
            HttpDsl.http("post todos")
            .post("/todos")
            .body(CoreDsl.StringBody(TestData.todo().toString()))
        )
}