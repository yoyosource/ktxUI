package de.yoyosource.ktxui.animation

import de.yoyosource.ktxui.Animation

fun delayedAnimation(delay: Long, action: (DelayedAnimation) -> Unit): DelayedAnimation {
    return DelayedAnimation(delay, action)
}

class DelayedAnimation(private val delay: Long, private val action: (DelayedAnimation) -> Unit) : Animation() {
    override fun animate(animationDuration: Long) {
        if (animationDuration >= delay) {
            stop()
            action(this)
        }
    }
}