import dto.Todo
import managers.TodoManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import utils.TestData
import utils.assertListContains
import utils.checkStatusCode
import utils.checkSuccess

class PostTodosTests {

    @Test
    fun postTodoTest() {
        TestData.todo().also {
            todos.addLast(it)
            TodoManager().apply {
                createTodo(it)
                    .checkSuccess()
                getTodos()
                    .assertListContains(it)
            }
        }
    }

    @TestFactory
    fun postTodoValidationTest(): List<DynamicTest> {
        return listOf(
            TestData.todo().apply { id = null },
            TestData.todo().apply { text = null },
            TestData.todo().apply { completed = null },
            TestData.todo().apply { id = null; text = null },
            TestData.todo().apply { text = null; completed = null },
            TestData.todo().apply { id = null; completed = null },
        )
            .map {
                DynamicTest.dynamicTest("Create todo $it") {
                    TodoManager().apply {
                        createTodo(it)
                            .checkStatusCode(400)
                    }
                }
            }
    }


    @Test
    fun postTodoWithLongDescriptionTest() {
        TestData.todo()
            .apply { text = "Longest de".repeat(1634) }
            .also {
                TodoManager().apply {
                    createTodo(it)
                        .checkStatusCode(413)
                }
            }
    }

    companion object {
        private var todos = mutableListOf<Todo>()

        @JvmStatic
        @AfterAll
        fun clearData() {
            TodoManager().apply {
                todos.map { it.id?.let { t -> deleteTodo(t) } }
            }
        }
    }
}