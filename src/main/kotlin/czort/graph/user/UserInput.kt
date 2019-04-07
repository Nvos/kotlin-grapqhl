package czort.graph.user

import org.hibernate.validator.constraints.Length
import org.jetbrains.exposed.sql.statements.UpdateBuilder

data class CreateUser(
  @field:Length(min = 10) val name: String
)

fun UpdateBuilder<Any>.from(input: CreateUser) = this.run {
    this[UserTable.name] = input.name
}