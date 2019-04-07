package czort.graph.note

import czort.graph.user.UserTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
class NotesRepository {

    fun findUserNotes(userId: UUID): List<Note> {
        return UserTable.select { UserTable.id eq userId }
          .toList()
          .toNotes()
    }

    fun findUserNotesByIds(userIds: List<UUID>): Map<UUID, List<Note>> {
        return NoteTable.select { NoteTable.createdBy inList userIds }
          .asSequence()
          .groupBy { it[NoteTable.id] }
          .mapValues { it.value.toNotes() }
    }
}