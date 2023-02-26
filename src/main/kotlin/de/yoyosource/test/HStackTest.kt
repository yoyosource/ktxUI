package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.views.HStack
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