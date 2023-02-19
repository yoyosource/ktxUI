package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation
import de.yoyosource.ktxui.ContainerAnimation

fun ContainerAnimation.loop(iterations: Int = -1, builder: ContainerAnimation.() -> Unit): Animation {
    return LoopAnimation(iterations).apply(builder).also(this::addAnimation)
}

private class LoopAnimation(private val iterations: Int) : ContainerAnimation {
    private val animations = mutableListOf<Animation>()
    private var currentIteration = 0
    private var onFinish: (() -> Unit)? = null

    override fun start() {
        if (iterations == 0) {
            onFinish?.invoke()
            return
        }
        currentIteration = 0
        animations.first().start()
    }

    override fun stop() {
        currentIteration = 0
        animations.forEach { it.stop() }
    }

    override fun onFinish(action: () -> Unit) {
        onFinish = action
    }

    override fun addAnimation(animation: Animation) {
        animations.lastOrNull()?.onFinish {
            animation.start()
        }
        animations.add(animation)
        animation.onFinish {
            if (iterations >= 0 && currentIteration == iterations) {
                currentIteration = 0
                onFinish?.invoke()
            } else {
                currentIteration++
                animations.first().start()
            }
        }
    }
}