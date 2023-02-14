package de.yoyosource.ktxui

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

val True by Observer(true)
val False by Observer(false)
val Zero by Observer(0)
val ZeroLong by Observer(0L)
val ZeroFloat by Observer(0f)
val ZeroDouble by Observer(0.0)
val EmptyString by Observer("")

private var _time by Observer(0L)
val Time by Observer(::_time) { it }
val TimeSeconds by Observer(::_time) { it / 1000.0 }

private var _deltaTimeTemp = System.currentTimeMillis()
val TimeDelta by Observer(::_time) {
    val delta = it - _deltaTimeTemp
    _deltaTimeTemp = it
    delta
}
val TimeDeltaSeconds by Observer(::TimeDelta) { it / 1000.0 }

interface Observer<T : Any> : ReadWriteProperty<Any?, T> {
    fun addObserver(observer: (T) -> Unit)
    fun removeObserver(observer: (T) -> Unit)

    companion object {

        operator fun <T : Any> invoke(): Observer<T> {
            return ValueObserver()
        }

        operator fun <T : Any> invoke(value: T): Observer<T> {
            return ValueObserver(value)
        }

        operator fun <S : Any, T : Any> invoke(source: KProperty0<S>, delegate: (S) -> T): Observer<T> {
            return DelegateObserver(source) {
                delegate(it[0] as S)
            }
        }

        operator fun <S1 : Any, S2 : Any, T : Any> invoke(source1: KProperty0<S1>, source2: KProperty0<S2>, delegate: (S1, S2) -> T): Observer<T> {
            return DelegateObserver(source1, source2) {
                delegate(it[0] as S1, it[1] as S2)
            }
        }

        operator fun <S1 : Any, S2 : Any, S3 : Any, T : Any> invoke(source1: KProperty0<S1>, source2: KProperty0<S2>, source3: KProperty0<S3>, delegate: (S1, S2, S3) -> T): Observer<T> {
            return DelegateObserver(source1, source2, source3) {
                delegate(it[0] as S1, it[1] as S2, it[2] as S3)
            }
        }

        operator fun <S1 : Any, S2 : Any, S3 : Any, S4 : Any, T : Any> invoke(source1: KProperty0<S1>, source2: KProperty0<S2>, source3: KProperty0<S3>, source4: KProperty0<S4>, delegate: (S1, S2, S3, S4) -> T): Observer<T> {
            return DelegateObserver(source1, source2, source3, source4) {
                delegate(it[0] as S1, it[1] as S2, it[2] as S3, it[3] as S4)
            }
        }

        operator fun <S1 : Any, S2 : Any, S3 : Any, S4 : Any, S5 : Any, T : Any> invoke(source1: KProperty0<S1>, source2: KProperty0<S2>, source3: KProperty0<S3>, source4: KProperty0<S4>, source5: KProperty0<S5>, delegate: (S1, S2, S3, S4, S5) -> T): Observer<T> {
            return DelegateObserver(source1, source2, source3, source4, source5) {
                delegate(it[0] as S1, it[1] as S2, it[2] as S3, it[3] as S4, it[4] as S5)
            }
        }

        operator fun <S: Any, T : Any> invoke(source1: KProperty0<S>, source2: KProperty0<S>, source3: KProperty0<S>, source4: KProperty0<S>, source5: KProperty0<S>, vararg rest: KProperty0<S>, delegate: (List<S>) -> T): Observer<T> {
            return DelegateObserver(source1, source2, source3, source4, source5, *rest) {
                delegate(it as List<S>)
            }
        }

        init {
            Thread {
                while (true) {
                    Thread.sleep(1000 / 60)
                    _time = System.currentTimeMillis()
                }
            }.start()
        }
    }
}

abstract class ObserverImpl<T : Any> : Observer<T> {
    private var observers = mutableSetOf<(T) -> Unit>()

    internal fun notifyObservers(value: T) {
        observers.forEach { it(value) }
    }

    override fun addObserver(observer: (T) -> Unit) {
        observers.add(observer)
    }

    override fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }
}

class ValueObserver<T : Any> : ObserverImpl<T> {
    private lateinit var value: T

    constructor()

    constructor(value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        notifyObservers(value)
    }
}

class DelegateObserver<T : Any> : ObserverImpl<T> {
    private lateinit var value: T

    constructor(vararg sources: KProperty0<Any>, delegate: (List<Any>) -> T) {
        val valueCreator = {
            value = delegate(sources.map { it.get() })
        }
        valueCreator()
        sources.forEach {
            val sourceProperty = it.apply {
                isAccessible = true
            }.getDelegate()
            if (sourceProperty !is Observer<*>) {
                throw IllegalArgumentException("Source property must be an observer")
            }

            sourceProperty.addObserver {
                valueCreator()
                notifyObservers(value)
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        throw UnsupportedOperationException("Cannot set value of delegate observer")
    }
}
