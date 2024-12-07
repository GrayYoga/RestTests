package api

import Config

object Endpoints {
    fun todos() = Config.baseUrl + "/todos"
    fun todos(id: ULong) = Config.baseUrl + "/todos/$id"
    fun ws() = Config.baseUrl + "/ws"
}
