package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation
import de.yoyosource.ktxui.ContainerAnimation

fun ContainerAnimation.parallel(builder: ContainerAnimation.() -> Unit): Animation {
    return ParallelAnimation().apply(builder).also(this::addAnimation)
}

private class ParallelAnimation : ContainerAnimation {
    private val animations = mutableListOf<Animation>()
    private var finishedAnimations = 0
    private var onFinish: (() -> Unit)? = null

    override fun start() {
        finishedAnimations = 0
        animations.forEach { it.start() }
    }

    override fun stop() {
        finishedAnimations = 0
        animations.forEach { it.stop() }
    }

    override fun onFinish(action: () -> Unit) {
        onFinish = action
    }

    override fun addAnimation(animation: Animation) {
        animations.add(animation)
        animation.onFinish {
            finishedAnimations++
            if (finishedAnimations == animations.size) {
                onFinish?.invoke()
            }
        }
    }
}