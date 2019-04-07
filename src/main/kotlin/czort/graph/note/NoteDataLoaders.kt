package czort.graph.note

import org.dataloader.DataLoader
import org.dataloader.MappedBatchLoaderWithContext
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
@Transactional(readOnly = true)
class NoteDataLoaders(
  val notesRepository: NotesRepository
) {
    companion object {
        const val NOTES_BY_USER_ID = "NOTES_BY_USER_ID"
    }

    fun createNotesByUserIdsLoader(): DataLoader<UUID, List<Note>> {
        val batchLoader = MappedBatchLoaderWithContext<UUID, List<Note>> { keys, _ ->
            CompletableFuture.supplyAsync {
                notesRepository.findUserNotesByIds(keys.toList())
            }
        }

        return DataLoader.newMappedDataLoader(batchLoader)
    }
}