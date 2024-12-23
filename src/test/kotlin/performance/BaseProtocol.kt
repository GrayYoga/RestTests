package performance

import io.gatling.javaapi.http.HttpDsl

class BaseProtocol {
    fun httpProtocol() = HttpDsl.http
        .baseUrl(Config.baseUrl) // Here is your baseURL
        .acceptHeader("application/json")
}