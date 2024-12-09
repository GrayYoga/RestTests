package dto

import com.fasterxml.jackson.annotation.JsonCreator

open class Entity @JsonCreator constructor(
    open var id: ULong?
)
