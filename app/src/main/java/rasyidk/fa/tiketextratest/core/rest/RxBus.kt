package rasyidk.fa.tiketextratest.core.rest

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {
    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}