package api

import java.lang.System.Logger.Level.INFO
import java.lang.System.getLogger
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.authorization.BasicAuthorization
import utils.JacksonMapper


object ApiClient {

    fun get(route: String, params: Map<String, String> = mapOf()): Response {
        return khttp.get(route, params = params)
            .apply { getLogger(ApiClient::class.java.name).log(INFO, "${this.statusCode} ${this.text}") }
    }

    fun post(route: String, body: Any): Response {
        return khttp.post(
            route,
            headers = mapOf("Content-Type" to "application/json"),
            data = JacksonMapper.writeValueAsString(body)
        ).apply { getLogger(ApiClient::class.java.name).log(INFO, "${this.statusCode} ${this.text}") }
    }

    fun delete(
        route: String,
        params: Map<String, String> = mapOf(),
        auth: Authorization = BasicAuthorization("admin", "admin")
    ): Response {
        return khttp.delete(
            route,
            params = params,
            auth = auth
        ).apply { getLogger(ApiClient::class.java.name).log(INFO, "${this.statusCode} ${this.text}") }
    }

    fun put(route: String, body: Any): Response {
        return khttp.put(
            route,
            headers = mapOf("Content-Type" to "application/json"),
            data = JacksonMapper.writeValueAsString(body),
        ).apply { getLogger(ApiClient::class.java.name).log(INFO, "${this.statusCode} ${this.text}") }
    }
}
