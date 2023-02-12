package de.yoyosource.ktxui

abstract class Animation {

    private var finishCallback: () -> Unit = {}

    fun onFinish(callback: () -> Unit) = apply {
        finishCallback = callback
    }

    abstract fun animate(animationDuration: Long)

    fun start() = apply {
        AnimationManager.addAnimation(this)
    }

    fun stop() = apply {
        AnimationManager.removeAnimation(this)
        finishCallback()
    }
}

object AnimationManager {

    private val animations = mutableMapOf<Animation, Long>()

    fun addAnimation(animation: Animation) {
        animations[animation] = System.currentTimeMillis()
    }

    fun removeAnimation(animation: Animation) {
        animations.remove(animation)
    }

    init {
        Thread {
            while (true) {
                animations.forEach {
                    it.key.animate(System.currentTimeMillis() - it.value)
                }
                Thread.sleep(1000 / 60)
            }
        }.start()
    }
}