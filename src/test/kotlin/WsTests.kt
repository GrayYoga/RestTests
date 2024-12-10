import dto.Todo
import dto.WsMessage
import managers.TodoManager
import managers.WsManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestData
import utils.checkSuccess

class WsTests {

    @BeforeEach
    fun initWsManager() {
        wsManager.startListening()
    }

    @Test
    fun checkCreateTodoTest() {
        TodoManager().apply {
            TestData.todo().also {
                todos.addLast(it)
                createTodo(it)
                    .checkSuccess()
                wsManager.waitMessages(WsMessage(type = "new_todo", data = it))
            }
        }
    }

    @Test
    fun checkCreateMultiTodoTest() {
        TodoManager().apply {
            val ts = mutableListOf<Todo>()
            repeat(3) {
                TestData.todo().also {
                    todos.addLast(it)
                    ts.addLast(it)
                    createTodo(it)
                        .checkSuccess()
                }
            }
            ts.map {
                wsManager.waitMessages(WsMessage(type = "new_todo", data = it))
            }
        }
    }

    companion object {
        private var todos = mutableListOf<Todo>()
        private val wsManager = WsManager()

        @JvmStatic
        @AfterAll
        fun clearData() {
            TodoManager().apply {
                todos.map { it.id?.let { t -> deleteTodo(t) } }
            }
        }
    }
}
