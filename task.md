# Task
The attached image contains an application implementing the most straightforward TODO manager with CRUD operations. The image can be loaded via `docker load` and run using `docker run -p 8080:4242`.

The task includes two parts:

Firstly, it's required to write some tests for checking the functionality of the application. We don't provide strict specifications because the domain is simple enough. Therefore, it is necessary to come up with cases by yourself.
For each route, implement a maximum of 5 test cases. Any additional test cases should be provided as a checklist for future implementation.

Secondly, it's necessary to check the performance of the `POST /todos` endpoint. It's not required to draw graphs. Measurements and some summary are enough.

Note that it can be useful to run the application with `VERBOSE=1` to see more logs (`docker run -e VERBOSE=1`).

## Endpoints
The only entity here is TODO  represented by a structure with the following three fields:
* `id` — an unsigned 64-bit identifier
* `text` - description of TODO
* `completed` - whether the todo is completed or not

### `GET /todos`

Get a JSON list of TODOs.

Available query parameters:
* `offset` — how many TODOs should be skipped
* `limit` - the maximum number of TODOs to be returned

### `POST /todos`

Create a new TODO. This endpoint expects the whole `TODO` structure as the request body.

### `PUT /todos/:id`

Update an existing TODO with the provided one.

### `DELETE /todos/:id`

Delete an existing TODO.

The endpoint requires the `Authorization` request header containing the `admin:admin` credentials in the `Basic` authorization schema.

### `/ws`

WebSocket connection to receive updates about new TODOs.

Available on port 4242 and path `/ws`


# Задача
Прикрепленное изображение содержит приложение, реализующее самый простой менеджер TODO с операциями CRUD. Образ можно загрузить с помощью `docker load` и запустить с помощью `docker run -p 8080:4242`.

Задача состоит из двух частей:

Во-первых, необходимо написать несколько тестов для проверки функциональности приложения. Мы не предоставляем строгих спецификаций, поскольку домен достаточно прост. Поэтому необходимо придумать кейсы самостоятельно.

Для каждого маршрута реализуйте максимум 5 тестовых случаев. Любые дополнительные тестовые случаи должны быть предоставлены в качестве контрольного списка для будущей реализации.

Во-вторых, необходимо проверить производительность конечной точки `POST /todos`. Не обязательно рисовать графики. Достаточно измерений и некоторого резюме.

Обратите внимание, что может быть полезно запустить приложение с `VERBOSE=1`, чтобы увидеть больше журналов (`docker run -e VERBOSE=1`).

## Конечные точки
Единственная сущность здесь — TODO, представленная структурой со следующими тремя полями:
* `id` — беззнаковый 64-битный идентификатор
* `text` — описание TODO
* `completed` — завершено ли todo или нет

### `GET /todos`

Получить список TODO в формате JSON.

Доступные параметры запроса:
* `offset` — сколько TODO следует пропустить
* `limit` — максимальное количество возвращаемых TODO

### `POST /todos`

Создать новый TODO. Эта конечная точка ожидает всю структуру `TODO` в качестве тела запроса.

### `PUT /todos/:id`

Обновить существующий TODO предоставленным.

### `DELETE /todos/:id`

Удалить существующий TODO.

Конечной точке требуется заголовок запроса `Authorization`, содержащий учетные данные `admin:admin` в схеме авторизации `Basic`.

### `/ws`

Подключение WebSocket для получения обновлений о новых TODO.

Доступно на порту 4242 и пути `/ws`
