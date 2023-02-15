package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.views.Text
import de.yoyosource.ktxui.views.ZStack

fun main() {
    val screen = Screen {
        ZStack {
            Text("First Text   ")
            Text("Second Text")
        }
    }

    KtxUIFrame(screen)
}