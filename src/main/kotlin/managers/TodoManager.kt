package managers

import api.ApiClient
import api.Endpoints
import dto.Todo
import khttp.responses.Response

class TodoManager {
    fun createTodo(todo: Todo): Response {
        return ApiClient().post(Endpoints.todos(), todo)
    }

    fun getTodos(offset: Int? = 0, limit: Int? = 100): Response {
        return ApiClient().get(Endpoints.todos())
    }
}
