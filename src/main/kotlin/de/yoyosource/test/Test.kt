package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.api.data.TextAlignment
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.Keybind
import de.yoyosource.ktxui.api.views.special.Dynamic
import de.yoyosource.ktxui.api.views.special.Empty

private var dynamicValue: SingleViewBuilder by Observer { Empty() }

fun main() {
    val screen = Screen {
        HCenter {
            Dynamic(::dynamicValue)
        }
    }

    KtxUIFrame(screen)

    Thread.sleep(5000)
    dynamicValue = {
        VStack {
            Text("Hello World\nPress enter to continue")
                .alignment(TextAlignment.CENTER)
            Keybind {
                keyChar('\n')
            }.onType { _, _, _ ->
                dynamicValue = {
                    Text("Hello YoyoNow")
                }
            }
        }
    }
}