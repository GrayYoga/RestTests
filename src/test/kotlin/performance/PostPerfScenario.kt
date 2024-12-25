package performance

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dto.Todo
import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl
import managers.TodoManager
import utils.checkSuccess
import utils.listOfEntity


object PostPerfScenario {
    private val mapper = jacksonObjectMapper()
    private var initialId = TodoManager().getTodos()
        .checkSuccess()
        .listOfEntity(Todo::class.java)
        .maxOfOrNull { it.id!! }
        ?: 0u

    private fun feeder(): Iterator<Map<String, String>> = iterator {
        while (true) {
            yield(
                mapOf(
                    "todo" to mapper.writeValueAsString(
                        Todo(
                            id = ++initialId,
                            text = "Todo $initialId",
                            completed = (initialId % 2u).toInt() == 0
                        )
                    )
                )
            )
        }
    }

    fun scn() =
        CoreDsl.scenario("performance.PostPerfSimulation")
            .feed(feeder())
            .exec(
                HttpDsl.http("post todos")
                    .post("/todos")
                    .body(
                        CoreDsl.StringBody("#{todo}")
                    )
            )
}