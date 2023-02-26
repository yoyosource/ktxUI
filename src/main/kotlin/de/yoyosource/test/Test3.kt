package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.api.data.TextAlignment
import de.yoyosource.ktxui.api.data.Side
import de.yoyosource.ktxui.api.data.black
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.layout.ZStack
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.onClick
import de.yoyosource.ktxui.views.shapes.RoundedRectangle
import de.yoyosource.ktxui.views.size.AbsoluteSize
import de.yoyosource.ktxui.views.size.FitSize
import de.yoyosource.ktxui.views.size.Size
import de.yoyosource.ktxui.views.size.ScreenSize
import java.awt.Color

fun main() {
    val screen = Screen {
        Padding {
            ScreenSize {screenWidth, screenHeight ->
                FitSize { width, height ->
                    ZStack {
                        RoundedRectangle()
                            .color(Color.GRAY)
                            .width(screenWidth)
                            .height(height)
                            .arcWidth(height)
                            .arcHeight(height)
                        Size { innerWidth, innerHeight, use ->
                            AbsoluteSize(screenWidth, innerHeight) {
                                HCenter {
                                    Text("Hello world")
                                        .border(black)
                                        .padding(Side.TOP, 10)
                                        .padding(Side.BOTTOM, 15)
                                        .alignment(TextAlignment.CENTER)
                                }.use()
                            }
                        }
                    }.onClick { viewPosX, viewPosY, relativeX, relativeY, x, y ->
                        println("Hello World")
                    }
                }
            }
        }.padding(10)
    }

    KtxUIFrame(screen).apply {}
}