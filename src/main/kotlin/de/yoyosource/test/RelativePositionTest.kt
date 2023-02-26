package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.layout.Divider
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.layout.VCenter
import de.yoyosource.ktxui.views.position.RelativePosition

fun main() {
    val screen = Screen {
        VCenter {
            Divider()
            RelativePosition(50, -50) {
                Text("Hello World")
            }
        }
    }

    KtxUIFrame(screen)
}