import dto.Todo
import khttp.structures.authorization.BasicAuthorization
import managers.TodoManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import utils.TestData
import utils.assertListDoesNotContains
import utils.checkStatusCode
import utils.checkSuccess

class DeleteTodosTests {
    private var todos = mutableListOf<Todo>()

    @BeforeEach
    fun prepareData() {
        TodoManager().apply {
            todos.apply {
                repeat(10) {
                    this.addLast(TestData.todo())
                    createTodo(this.last()).checkSuccess()
                }
            }
        }
    }

    @AfterEach
    fun clearData() {
        TodoManager().apply {
            todos.map { it.id?.let { t -> deleteTodo(t) } }
        }
    }

    @Test
    fun deleteTodoTest() {
        TodoManager().apply {
            deleteTodo(todos.first().id!!)
                .checkSuccess()
            getTodos()
                .checkSuccess()
                .assertListDoesNotContains(todos.first())
        }
    }

    @Test
    fun deleteNonExistentTodoTest() {
        TodoManager().apply {
            deleteTodo(TestData.nextId())
                .checkStatusCode(404)
        }
    }

    @Test
    fun deleteDuplicateTodoTest() {
        TodoManager().apply {
            val todo1 = TestData.todo().also { t -> todos.addLast(t) }
            val todo2 = TestData.todo().also { t -> todos.addLast(t) }
            createTodo(todo1).checkSuccess()
            createTodo(todo2).checkSuccess()
            updateTodo(todo1.id!!, todo2).checkStatusCode(200)

            deleteTodo(todo2.id!!)
                .checkSuccess()
            getTodos()
                .checkSuccess()
                .assertListDoesNotContains(todo1, todo2)
        }
    }

    @TestFactory
    fun deleteTodoWithoutAuthTest(): List<DynamicTest> {
        return listOf(
            Pair("", ""),
            Pair(" ", " "),
            Pair("admin", ""),
            Pair("", "admin"),
            Pair("user", "user")
        ).map {
            DynamicTest.dynamicTest("deleteTodoWitAuth [${it.first}]:[${it.second}]") {
                TodoManager().apply {
                    val todo = TestData.todo().also { t -> todos.addLast(t) }
                    deleteTodo(todo.id!!, auth = BasicAuthorization(it.first, it.second))
                        .checkStatusCode(401)
                }
            }
        }
    }
}
