package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.views.Screen
import de.yoyosource.ktxui.animation.animation
import de.yoyosource.ktxui.animation.delay
import de.yoyosource.ktxui.animation.dynamicSet
import de.yoyosource.ktxui.animation.loop
import de.yoyosource.ktxui.views.Conditional
import de.yoyosource.ktxui.views.Text
import de.yoyosource.ktxui.views.VStack

var bool by Observer(true)

fun main() {
    val screen = Screen {
        VStack {
            Conditional(true) {
                Text("Hello World")
            }
            Conditional(::bool) {
                Text("Hello World")
            }
        }
    }

    KtxUIFrame(screen)

    animation {
        loop {
            dynamicSet(::bool) { !bool }
            delay(1000)
        }
    }.start()
}