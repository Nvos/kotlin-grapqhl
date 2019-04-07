package czort.graph.user

import com.coxautodev.graphql.tools.GraphQLResolver
import czort.graph.note.Note
import czort.graph.note.NotesRepository
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Component

@Component
class UserResolver(
    val notesRepository: NotesRepository
): GraphQLResolver<User> {

    @Timed("notes")
    fun notes(parent: User): List<Note> {
        return notesRepository.findUserNotes(parent.id)
    }
}