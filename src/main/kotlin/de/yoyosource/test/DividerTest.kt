package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.views.Screen
import de.yoyosource.ktxui.views.Divider
import de.yoyosource.ktxui.views.Text
import de.yoyosource.ktxui.views.VStack

fun main() {
    val screen = Screen {
        VStack {
            Text("First Text")
            Divider()
            Text("Second Text")
        }
    }

    KtxUIFrame(screen)
}