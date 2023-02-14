package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.animation.DelayedAnimation
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

    DelayedAnimation(100) {
        bool = !bool
        it.start()
    }.start()
}