package dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Todo @JsonCreator constructor(
    val id: ULong?,
    val text: String?,
    val completed: Boolean?,
)
