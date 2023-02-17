package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation
import de.yoyosource.ktxui.ContainerAnimation

fun animation(builder: ContainerAnimation.() -> Unit): Animation {
    return SequenceAnimation().apply(builder)
}

private class SequenceAnimation : ContainerAnimation {
    private val animations = mutableListOf<Animation>()
    private var onFinish: (() -> Unit)? = null

    override fun start() {
        animations.first().start()
    }

    override fun stop() {
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
            onFinish?.invoke()
        }
    }
}