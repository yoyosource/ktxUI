package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.Keybind
import de.yoyosource.ktxui.views.utils.Dynamic
import de.yoyosource.ktxui.views.utils.Empty

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