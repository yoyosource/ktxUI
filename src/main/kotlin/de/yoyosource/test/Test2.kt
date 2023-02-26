package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.animation.animation
import de.yoyosource.ktxui.animation.delay
import de.yoyosource.ktxui.animation.set
import de.yoyosource.ktxui.utils.Side
import de.yoyosource.ktxui.views.Screen
import de.yoyosource.ktxui.views.Text
import de.yoyosource.ktxui.views.ZStack
import de.yoyosource.ktxui.views.combound.Tooltip
import de.yoyosource.ktxui.views.padding
import de.yoyosource.ktxui.views.shapes.RoundedRectangle
import de.yoyosource.ktxui.views.size.FitSize
import java.awt.Color

var text by Observer("Hello World")

fun main() {
    val screen = Screen {
        Tooltip {
            Text("Hello World")
        }.setTooltip {
            FitSize { width, height ->
                ZStack {
                    RoundedRectangle()
                        .color(Color.GRAY)
                        .width(width)
                        .height(height)
                        .arcWidth(5)
                        .arcHeight(5)
                    Text(::text)
                        .padding(5)
                        .padding(Side.BOTTOM, 5)
                        .padding(Side.TOP, 1)
                }
            }
        }
    }

    KtxUIFrame(screen).apply {
    }

    animation {
        delay(5000)
        set(::text, "Hello World, YoyoNow")
        delay(5000)
        set(::text, "Hello World")
    }.start()
}