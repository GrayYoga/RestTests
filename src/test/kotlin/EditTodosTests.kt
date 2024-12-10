import dto.Todo
import managers.TodoManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import utils.TestData
import utils.assertListContains
import utils.checkStatusCode
import utils.checkSuccess
import utils.listOfEntity

class EditTodosTests : BaseTodoTests() {

    @Test
    fun editTodoTest() {
        TodoManager().apply {
            TestData.todo().also {
                todos.addLast(it)
                createTodo(it).checkSuccess()
                getTodos().assertListContains(it)

                val updated = Todo(
                    id = TestData.nextId(), text = "Updated description", completed = true
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
        ).map {
            DynamicTest.dynamicTest("Create todo $it") {
                TodoManager().apply {
                    createTodo(TestData.todo().also { t -> todos.addLast(t) }).checkSuccess()
                    updateTodo(todos.last().id!!, it).checkStatusCode(401) // bug here: 401 when incorrect data
                }
            }
        }
    }

    @Test
    fun editNonExistentTodoTest() {
        TodoManager().apply {
            createTodo(TestData.todo().also { t -> todos.addLast(t) }).checkSuccess()

            val updated = Todo(
                id = TestData.nextId(), text = "Updated description", completed = true
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

    @Test
    fun editTodoDuplicateTest() {
        TodoManager().apply {
            val todo1 = TestData.todo().also { t -> todos.addLast(t) }
            val todo2 = TestData.todo().also { t -> todos.addLast(t) }
            createTodo(todo1).checkSuccess()
            createTodo(todo2).checkSuccess()
            updateTodo(todo1.id!!, todo2).checkStatusCode(200) // bug here? Does can create duplicates when editing?

            assertThat(
                getTodos().checkSuccess().listOfEntity(Todo::class.java)
                    .filter { it.id == todo2.id }.size
            ).isEqualTo(2)
        }
    }
}
