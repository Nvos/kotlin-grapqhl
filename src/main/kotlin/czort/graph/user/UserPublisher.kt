package czort.graph.user

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import org.springframework.stereotype.Component

@Component
class UserPublisher {

    final val publisher: Flowable<User>
    private final val subject = BehaviorSubject.create<User>()!!

    init {
        val connectableObservable = subject.share().publish()
        connectableObservable.connect()
        publisher = connectableObservable.toFlowable(BackpressureStrategy.LATEST)
    }

    fun publish(comment: User) {
        subject.onNext(comment)
    }
}