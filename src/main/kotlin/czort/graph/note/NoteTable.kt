package czort.graph.note

import czort.graph.user.UserTable
import org.jetbrains.exposed.sql.Table
import java.util.*

object NoteTable: Table() {
    val id = uuid("id").primaryKey().clientDefault { UUID.randomUUID() }
    val title = text("title")
    val text = text("text")
    val createdBy = uuid("created_by_id") references UserTable.id
}
