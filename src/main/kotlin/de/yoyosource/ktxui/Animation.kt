package de.yoyosource.ktxui

@DslMarker
annotation class KtxAnimationDsl

@KtxAnimationDsl
interface Animation {
    fun start()
    fun stop()
    fun onFinish(action: () -> Unit)
}

interface ElementAnimation : Animation {
    fun animate(duration: Long, delta: Double)
}

interface ContainerAnimation : Animation {
    fun addAnimation(animation: Animation)
}

object AnimationManager {
    private val animations = mutableMapOf<ElementAnimation, Long>()

    fun addAnimation(animation: ElementAnimation) {
        animations[animation] = System.currentTimeMillis()
    }

    fun removeAnimation(animation: ElementAnimation) {
        animations.remove(animation)
    }

    init {
        Thread {
            var lastTime = System.currentTimeMillis()
            while (true) {
                val currentTime = System.currentTimeMillis()
                animations.forEach { (animation, startTime) ->
                    animation.animate(currentTime - startTime, (currentTime - lastTime) / 1000.0)
                }
                lastTime = currentTime
                Thread.sleep(1000 / 60)
            }
        }.start()
    }
}