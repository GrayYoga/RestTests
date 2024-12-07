import managers.TodoManager
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import utils.TestData
import utils.assertListContains
import utils.checkSuccess


class GetTodosTests {

    @Test
    fun getTodosTest() {
        TodoManager()
            .getTodos()
            .checkSuccess()
            .assertListContains(
                todo
            )
    }

    companion object {
        val todo = TestData.todo()

        @JvmStatic
        @BeforeAll
        fun createTodo(): Unit {
            TodoManager()
                .createTodo(todo)
                .checkSuccess()
        }
    }
}
