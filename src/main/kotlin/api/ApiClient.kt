package api

import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.authorization.BasicAuthorization
import utils.JacksonMapper
import utils.ResponseLogger.logResponse


object ApiClient {

    fun get(route: String, params: Map<String, String> = mapOf()): Response {
        return khttp.get(route, params = params)
            .apply { logResponse("get", this) }
    }

    fun post(route: String, body: Any): Response {
        return khttp.post(
            route,
            headers = mapOf("Content-Type" to "application/json"),
            data = JacksonMapper.writeValueAsString(body)
        ).apply { logResponse("post", this) }
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
        ).apply { logResponse("delete", this) }
    }

    fun put(route: String, body: Any): Response {
        return khttp.put(
            route,
            headers = mapOf("Content-Type" to "application/json"),
            data = JacksonMapper.writeValueAsString(body),
        ).apply { logResponse("put", this) }
    }
}
