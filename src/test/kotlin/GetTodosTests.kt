import dto.Todo
import managers.TodoManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import utils.TestData
import utils.assertListContains
import utils.assertListSize
import utils.checkStatusCode
import utils.checkSuccess


class GetTodosTests : BaseTodoTests() {

    @Test
    fun getTodosTest() {
        TodoManager()
            .getTodos()
            .checkSuccess()
            .assertListContains(
                todo
            )
    }

    @TestFactory
    fun getTodosOffsetTest(): List<DynamicTest> {
        return listOf(0, 5, 10, 11).map {
            DynamicTest.dynamicTest("Get todos: offset=$it") {
                TodoManager()
                    .getTodos(offset = it, limit = 11 - it)
                    .checkSuccess()
                    .assertListSize(11 - it)
            }
        }
    }

    @TestFactory
    fun getTodosValidateOffsetTest(): List<DynamicTest> {
        return listOf(-1, "a", "%").map {
            DynamicTest.dynamicTest("Get todos: offset=$it") {
                TodoManager()
                    .getTodos(offset = it)
                    .checkStatusCode(400)
            }
        }
    }

    @TestFactory
    fun getTodosLimitTest(): List<DynamicTest> {
        return listOf(-1, "a", "%").map {
            DynamicTest.dynamicTest("Get todos: limit=$it") {
                TodoManager()
                    .getTodos(limit = it)
                    .checkStatusCode(400)
            }
        }
    }

    companion object {
        private var todos = mutableListOf<Todo>()
        val todo = TestData.todo().also { todos.addLast(it) }

        @JvmStatic
        @BeforeAll
        fun createTodo(): Unit {
            TodoManager().apply {
                createTodo(todo)
                repeat(10) {
                    createTodo(TestData.todo().also { todos.addLast(it) })
                        .checkSuccess()
                }
            }
        }
    }
}
