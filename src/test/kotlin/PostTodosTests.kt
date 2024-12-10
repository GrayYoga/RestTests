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

class PostTodosTests : BaseTodoTests() {

    @Test
    fun postTodoTest() {
        TodoManager().apply {
            TestData.todo().also {
                todos.addLast(it)
                createTodo(it)
                    .checkSuccess()
                getTodos()
                    .assertListContains(it)
            }
        }
    }

    @Test
    fun postDuplicateTodoTest() {
        TodoManager().apply {
            TestData.todo().also {
                todos.addLast(it)
                createTodo(it)
                    .checkSuccess()
                createTodo(it)
                    .checkStatusCode(400)
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
        TodoManager().apply {
            TestData.todo()
                .apply { text = "Longest de".repeat(1635) }
                .also {
                    createTodo(it)
                        .checkStatusCode(413)
                }
        }
    }
}
