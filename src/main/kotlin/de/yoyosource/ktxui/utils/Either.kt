package de.yoyosource.ktxui.utils

sealed interface Either<A, B> {

    class Left<A, B>(val value: A) : Either<A, B> {
        override val isLeft: Boolean
            get() = true
        override val isRight: Boolean
            get() = false

        override fun left(): A? = value
        override fun right(): B? = null
    }

    class Right<A, B>(val value: B) : Either<A, B> {
        override val isLeft: Boolean
            get() = false
        override val isRight: Boolean
            get() = true

        override fun left(): A? = null
        override fun right(): B? = value
    }

    val isLeft: Boolean
    val isRight: Boolean

    fun left(): A?
    fun right(): B?
}
