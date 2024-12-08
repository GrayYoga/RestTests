package dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Todo @JsonCreator constructor(
    var id: ULong?,
    var text: String?,
    var completed: Boolean?,
)
