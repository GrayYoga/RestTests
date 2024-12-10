package managers

import api.WsClient
import dto.WsMessage
import java.time.Duration
import org.awaitility.Awaitility
import org.awaitility.kotlin.withPollInterval

class WsManager {
    private val wsClient = WsClient()

    fun waitMessages(vararg messages: WsMessage, timeoutSeconds: Long = 10, intervalMillis: Long = 100) {
        Awaitility.await()
            .atMost(Duration.ofSeconds(timeoutSeconds))
            .withPollInterval(Duration.ofMillis(intervalMillis))
            .until {
                wsClient.listOfWsMessages.containsAll(messages.toList())
            }
    }

    fun startListening() {
        wsClient.start()
    }
}
