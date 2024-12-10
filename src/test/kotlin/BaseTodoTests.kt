import dto.Todo
import managers.TodoManager
import org.junit.jupiter.api.AfterAll

open class BaseTodoTests {
    companion object {
        var todos = mutableListOf<Todo>()

        @JvmStatic
        @AfterAll
        fun clearData() {
            TodoManager().apply {
                todos.map { it.id?.let { t -> deleteTodo(t) } }
            }
        }
    }
}
