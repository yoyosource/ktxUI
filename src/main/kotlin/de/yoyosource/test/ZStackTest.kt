package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.api.views.layout.ZStack

fun main() {
    val screen = Screen {
        ZStack {
            Text("First Text   ")
            Text("Second Text")
        }
    }

    KtxUIFrame(screen)
}