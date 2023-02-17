package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.AnimationManager
import de.yoyosource.ktxui.ContainerAnimation
import de.yoyosource.ktxui.ElementAnimation

fun ContainerAnimation.delay(duration: Long) {
    addAnimation(DelayAnimation(duration))
}

private class DelayAnimation(private val duration: Long): ElementAnimation {
    private var onFinish: (() -> Unit)? = null

    override fun start() {
        AnimationManager.addAnimation(this)
    }

    override fun stop() {
        AnimationManager.removeAnimation(this)
    }

    override fun onFinish(action: () -> Unit) {
        onFinish = action
    }

    override fun animate(duration: Long, delta: Double) {
        if (duration >= this.duration) {
            stop()
            onFinish?.invoke()
        }
    }
}