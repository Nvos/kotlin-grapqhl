package czort.graph.note

import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class Note(
  val id: UUID,
  val title: String,
  val text: String
)

fun List<ResultRow>.toNotes(): List<Note> {
    return this.map {
        Note(
          id = it[NoteTable.id],
          text = it[NoteTable.text],
          title =  it[NoteTable.title]
        )
    }
}