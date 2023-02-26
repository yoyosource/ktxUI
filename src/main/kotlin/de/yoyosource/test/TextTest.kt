package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.Text

fun main() {
    val screen = Screen {
        Text("Hello World")
    }

    KtxUIFrame(screen)
}