package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation
import de.yoyosource.ktxui.ContainerAnimation
import kotlin.reflect.KMutableProperty0

fun <T> ContainerAnimation.set(toAnimate: KMutableProperty0<T>, value: T) {
    addAnimation(SetAnimation(toAnimate) { value })
}

fun <T> ContainerAnimation.dynamicSet(toAnimate: KMutableProperty0<T>, value: () -> T) {
    addAnimation(SetAnimation(toAnimate, value))
}

private class SetAnimation<T>(private val toAnimate: KMutableProperty0<T>, private val valueFunction: () -> T) : Animation {
    private var onFinish: (() -> Unit)? = null

    override fun start() {
        toAnimate.set(valueFunction())
        onFinish?.invoke()
    }

    override fun stop() {
    }

    override fun onFinish(action: () -> Unit) {
        onFinish = action
    }
}