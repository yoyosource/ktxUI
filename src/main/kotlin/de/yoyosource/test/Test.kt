package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.views.HCenter
import de.yoyosource.ktxui.views.Text
import de.yoyosource.ktxui.views.TextAlignment
import de.yoyosource.ktxui.views.VCenter

fun main() {
    val screen = Screen {
        VCenter {
            HCenter {
                Text("Hello World\nHello from another World")
                    .alignment(TextAlignment.CENTER)
            }
        }
    }

    KtxUIFrame(screen)
}