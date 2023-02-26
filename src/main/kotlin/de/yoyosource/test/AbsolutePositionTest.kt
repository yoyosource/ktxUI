package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.views.position.AbsolutePosition
import de.yoyosource.ktxui.api.views.Text

fun main() {
    val screen = Screen {
        AbsolutePosition(100, 50) {
            Text("Hello World")
        }
    }

    KtxUIFrame(screen)
}