package czort.graph

import czort.graph.note.NoteTable
import czort.graph.user.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
@Transactional
class GraphApplication: CommandLineRunner {
    override fun run(vararg args: String?) {
        SchemaUtils.create(
          UserTable, NoteTable
        )
    }

}

fun main(args: Array<String>) {
    runApplication<GraphApplication>(*args)
}
