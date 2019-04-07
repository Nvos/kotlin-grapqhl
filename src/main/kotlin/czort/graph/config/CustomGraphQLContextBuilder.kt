package czort.graph.config

import czort.graph.note.NoteDataLoaders
import graphql.servlet.GraphQLContext
import graphql.servlet.GraphQLContextBuilder
import org.dataloader.DataLoaderRegistry
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.websocket.Session
import javax.websocket.server.HandshakeRequest

@Component
class CustomGraphQLContextBuilder(
  val noteDataLoaders: NoteDataLoaders
): GraphQLContextBuilder {

    override fun build(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): GraphQLContext {
        val context = GraphQLContext(httpServletRequest, httpServletResponse)
        context.setDataLoaderRegistry(createDataLoaderRegistry())

        return context
    }

    override fun build(session: Session, handshakeRequest: HandshakeRequest): GraphQLContext {
        val context = GraphQLContext(session, handshakeRequest)
        context.setDataLoaderRegistry(createDataLoaderRegistry())

        return context
    }

    override fun build(): GraphQLContext {
        val context = GraphQLContext()
        context.setDataLoaderRegistry(createDataLoaderRegistry())

        return context
    }

    fun createDataLoaderRegistry(): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
        registry.register(NoteDataLoaders.NOTES_BY_USER_ID, noteDataLoaders.createNotesByUserIdsLoader())

        return registry
    }
}