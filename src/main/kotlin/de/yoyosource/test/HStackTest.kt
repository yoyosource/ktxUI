package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.layout.HStack
import de.yoyosource.ktxui.api.views.Text

fun main() {
    val screen = Screen {
        HStack {
            Text("First Text   ")
            Text("Second Text")
        }
    }

    KtxUIFrame(screen)
}