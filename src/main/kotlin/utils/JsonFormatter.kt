package utils

import java.lang.System.Logger.Level.INFO
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject

object ResponseLogger {
    fun logResponse(name: String, response: Response) {
        System.getLogger(name).log(
            INFO,
            "${response.request.method} ${response.request.url}\n${
                jsonFormat(response.request.data?.toString())
            }"
        )
        System.getLogger(name).log(
            INFO, "${response.statusCode}\n${
                jsonFormat(response.text)
            }"
        )
    }

    fun jsonFormat(data: String?): String {
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