package czort.graph.user

import org.jetbrains.exposed.sql.Table
import java.util.*

object UserTable: Table() {
    val id = uuid("id").primaryKey().clientDefault { UUID.randomUUID() }
    val name = text("name")
}