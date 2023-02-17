package de.yoyosource.ktxui.animation2

import de.yoyosource.ktxui.Observer
import kotlin.reflect.KMutableProperty0

var testInt by Observer(0)

fun main() {
    animation {
        delay(5000)
        loop {
            set(::testInt, 100)
            delay(1000)
            set(::testInt, 0)
            delay(1000)
        }
    }
}

fun animation(builder: Animation.() -> Unit): Animation {
    TODO()
}

interface Animation {
    fun start()
    fun stop()

    fun animate(animationDuration: Long)
    fun onFinish(action: () -> Unit)
}

interface Interpolator<T> {
    fun interpolate(from: T, to: T, animationDuration: Long): Pair<T, Boolean>
}

fun Animation.delay(delay: Long) {
    TODO()
}

fun <T> Animation.set(toAnimate: KMutableProperty0<T>, to: T) = interpolate(toAnimate, to, object : Interpolator<T> {
    override fun interpolate(from: T, to: T, timeSinceStart: Long): Pair<T, Boolean> {
        return to to true
    }
})

fun <T> Animation.interpolate(toAnimate: KMutableProperty0<T>, to: T, interpolator: Interpolator<T>) {
    TODO()
}

fun Animation.loop(iterations: Int = -1, builder: Animation.() -> Unit) {
    TODO()
}

fun Animation.parallel(builder: Animation.() -> Unit) {
    TODO()
}