package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.views.AbsolutePosition
import de.yoyosource.ktxui.views.Text

fun main() {
    val screen = Screen {
        AbsolutePosition(100, 50) {
            Text("Hello World")
        }
    }

    KtxUIFrame(screen)
}