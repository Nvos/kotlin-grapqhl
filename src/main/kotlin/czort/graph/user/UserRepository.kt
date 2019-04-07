package czort.graph.user

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class UserRepository(
  val userPublisher: UserPublisher
) {

    fun insertUser(input: CreateUser): User {
        val result = UserTable
          .insert { it.from(input) } get UserTable.id

        val user = User(id = result!!, name = input.name)

        userPublisher.publish(user)
        return user
    }

    fun findAll(): List<User> {
        return UserTable.selectAll().toList().toUsers()
    }

    fun findById(id: UUID): User {
        return UserTable.select { UserTable.id eq id }.first().toUser()
    }
}