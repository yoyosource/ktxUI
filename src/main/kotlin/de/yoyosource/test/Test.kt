package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.Keybind

fun main() {
    val screen = Screen {
        HCenter {
            Keybind {
                keyChar('a')
                ignoreCase(true)
            }.onPress { keyCode, keyChar, modifier ->
                println("Press: a")
            }.onRelease { keyCode, keyChar, modifier ->
                println("Release: a")
            }
            VCenter {
                Text("Hello World\nHello from another World").alignment(TextAlignment.CENTER)
            }
        }
    }

    KtxUIFrame(screen)
}