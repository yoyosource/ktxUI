package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.utils.Side
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.combound.Tooltip
import de.yoyosource.ktxui.views.shapes.RoundedRectangle
import de.yoyosource.ktxui.views.size.FitSize
import java.awt.Color

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
                    Text("Hello World")
                        .padding(5)
                        .padding(Side.BOTTOM, 5)
                        .padding(Side.TOP, 1)
                }
            }
        }
    }

    KtxUIFrame(screen)
}