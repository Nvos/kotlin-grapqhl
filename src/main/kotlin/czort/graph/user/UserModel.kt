package czort.graph.user

import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class User(
  val id: UUID,
  val name: String
)

fun ResultRow.toUser() = User(
  id = this[UserTable.id],
  name = this[UserTable.name]
)

fun List<ResultRow>.toUsers() = this.map { it.toUser() }