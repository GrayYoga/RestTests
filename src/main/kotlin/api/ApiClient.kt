package api

import java.lang.System.Logger.Level.INFO
import java.lang.System.getLogger
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.authorization.BasicAuthorization
import org.json.JSONArray
import org.json.JSONObject
import utils.JacksonMapper


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

    private fun logResponse(name: String, response: Response) {
        getLogger(name).log(
            INFO,
            "${response.request.method} ${response.request.url}\n${
                jsonFormat(response.request.data?.toString())
            }"
        )
        getLogger(name).log(
            INFO, "${response.statusCode}\n${
                jsonFormat(response.text)
            }"
        )
    }

    private fun jsonFormat(data: String?): String {
        return data?.let {
            if (it.startsWith("{") && it.endsWith("}")) {
                JSONObject(it).toString(2)
            } else if (it.startsWith("[{") && it.endsWith("}]")) {
                JSONArray(it).toString(2)
            } else {
                it
            }
        } ?: ""
    }
}
