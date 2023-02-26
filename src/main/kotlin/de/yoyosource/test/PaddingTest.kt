package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.views.Padding
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.views.padding

fun main() {
    val screen = Screen {
        Padding {
            Padding {
                Text("Hello World")
                    .padding(10)
                    .padding(20)
            }
        }.padding(10)
    }

    KtxUIFrame(screen)
}