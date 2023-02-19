package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.views.Screen
import de.yoyosource.ktxui.views.*

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