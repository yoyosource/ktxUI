package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.views.Screen
import de.yoyosource.ktxui.views.Text

fun main() {
    val screen = Screen {
        Text("Hello World")
    }

    KtxUIFrame(screen)
}