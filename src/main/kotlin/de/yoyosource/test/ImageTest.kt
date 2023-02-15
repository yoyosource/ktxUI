package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.views.Image

fun main() {
    val screen = Screen {
        Image("/test.png")
    }

    KtxUIFrame(screen)
}