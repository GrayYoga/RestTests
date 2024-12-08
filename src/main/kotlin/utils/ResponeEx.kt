package utils

import khttp.responses.Response
import org.assertj.core.api.Assertions.assertThat


fun Response.checkStatusCode(vararg codes: Int): Response {
    assertThat(this.statusCode)
        .isIn(codes.toList())
    return this
}

fun Response.checkSuccess() = checkStatusCode(200, 201, 204)

fun <T> Response.entity(type: Class<T>): T {
    return JacksonMapper.readValue(this.text, type)
}

fun <T> Response.listOfEntity(type: Class<T>): List<T> {
    return JacksonMapper.readValue(
        this.text, JacksonMapper.typeFactory.constructCollectionType(
            List::class.java,
            type
        )
    )
}

fun Response.assertEquals(expected: Any) {
    assertThat(this.entity(expected::class.java)).isEqualTo(expected)
}

fun Response.assertListContains(vararg expected: Any) {
    assertThat(this.listOfEntity(expected[0]::class.java))
        .isNotNull
        .containsAll(listOf(*expected))
}
