package api

import java.lang.System.Logger.Level.INFO
import java.lang.System.getLogger
import khttp.responses.Response
import utils.JacksonMapper


class ApiClient {

    fun get(route: String, params: Map<String, String> = mapOf()): Response {
        return khttp.get(route, params = params)
            .apply { getLogger(ApiClient::class.java.name).log(INFO, this.text) }
    }

    fun post(route: String, body: Any): Response {
        return khttp.post(
            route,
            headers = mapOf("Content-Type" to "application/json"),
            data = JacksonMapper.writeValueAsString(body)
        ).apply { getLogger(ApiClient::class.java.name).log(INFO, this.text) }
    }
}
