package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation
import kotlin.reflect.KMutableProperty0

fun linearAnimation(toAnimate: KMutableProperty0<Int>, to: Int, step: Int = 1): Animation {
    return LinearAnimation(toAnimate, to, step)
}

fun linearAnimation(toAnimate: KMutableProperty0<Long>, to: Long, step: Long = 1): Animation {
    return LinearAnimation(toAnimate, to, step)
}

fun linearAnimation(toAnimate: KMutableProperty0<Float>, to: Float, step: Float = 1.0f): Animation {
    return LinearAnimation(toAnimate, to, step)
}

fun linearAnimation(toAnimate: KMutableProperty0<Double>, to: Double, step: Double = 1.0): Animation {
    return LinearAnimation(toAnimate, to, step)
}

class LinearAnimation<T>(private val toAnimate: KMutableProperty0<T>, private val to: T, private val step: T) : Animation() {

    override fun animate(animationDuration: Long) {
        if (toAnimate.get() == to) {
            stop()
            return
        }
        val current = toAnimate.get()
        when(to) {
            is Int -> {
                val to = to as Int
                val current = current as Int
                if (current < to) {
                    toAnimate.set((current + step as Int).coerceAtMost(to) as T)
                } else {
                    toAnimate.set((current - step as Int).coerceAtLeast(to) as T)
                }
            }
            is Long -> {
                val to = to as Long
                val current = current as Long
                if (current < to) {
                    toAnimate.set((current + step as Long).coerceAtMost(to) as T)
                } else {
                    toAnimate.set((current - step as Long).coerceAtLeast(to) as T)
                }
            }
            is Float -> {
                val to = to as Float
                val current = current as Float
                if (current < to) {
                    toAnimate.set((current + step as Float).coerceAtMost(to) as T)
                } else {
                    toAnimate.set((current - step as Float).coerceAtLeast(to) as T)
                }
            }
            is Double -> {
                val to = to as Double
                val current = current as Double
                if (current < to) {
                    toAnimate.set((current + step as Double).coerceAtMost(to) as T)
                } else {
                    toAnimate.set((current - step as Double).coerceAtLeast(to) as T)
                }
            }
        }
    }
}