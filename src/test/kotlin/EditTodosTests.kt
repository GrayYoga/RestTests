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

class EditTodosTests {

    @Test
    fun editTodoTest() {
        TestData.todo().also {
            todos.addLast(it)
            TodoManager().apply {
                createTodo(it).checkSuccess()
                getTodos().assertListContains(it)

                val updated = Todo(
                    id = TestData.nextId(),
                    text = "Updated description",
                    completed = true
                )
                updateTodo(it.id!!, updated).checkSuccess()
                todos.addLast(updated)
                getTodos().assertListContains(updated)
            }
        }
    }

    @TestFactory
    fun editTodoValidationTest(): List<DynamicTest> {
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
                        createTodo(TestData.todo().also { t -> todos.addLast(t) })
                            .checkSuccess()
                        updateTodo(todos.last().id!!, it)
                            .checkStatusCode(401) // bug here: 401 when incorrect data
                    }
                }
            }
    }

    @Test
    fun editNonExistentTodoTest() {
        TodoManager().apply {
            createTodo(TestData.todo().also { t -> todos.addLast(t) }).checkSuccess()

            val updated = Todo(
                id = TestData.nextId(),
                text = "Updated description",
                completed = true
            )
            updateTodo(updated.id!!, updated).checkStatusCode(404)
        }
    }

    @Test
    fun editSameTodoTest() {
        TodoManager().apply {
            val todo = TestData.todo().also { t -> todos.addLast(t) }
            createTodo(todo).checkSuccess()
            updateTodo(todo.id!!, todo).checkStatusCode(200)
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
