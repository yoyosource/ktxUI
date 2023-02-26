package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.layout.Divider
import de.yoyosource.ktxui.api.views.Text
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