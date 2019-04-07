package czort.graph.note

import com.coxautodev.graphql.tools.GraphQLResolver
import czort.graph.user.User
import czort.graph.user.UserRepository
import czort.graph.user.UserTable
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class NoteResolver(
  private val userRepository: UserRepository
): GraphQLResolver<Note> {

    fun owner(parent: Note): User {

        return userRepository.findById(parent.id)
    }
}