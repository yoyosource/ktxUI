package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.*
import de.yoyosource.ktxui.views.shapes.Circle
import java.awt.Color

private var posX by Observer(0)
private var posY by Observer(0)

private var hovered by Observer(false)
private var relativePosX by Observer(0)
private var relativePosY by Observer(0)

private var image by ImageJarResource("/test.png")

fun main() {
    val screen = Screen {
        AbsolutePosition(::posX, ::posY) {
            AbsoluteSize(128, 128) {
                ZStack {
                    Image(::image)
                    Conditional(::hovered) {
                        RelativePosition(::relativePosX, ::relativePosY) {
                            Text("Hello World")
                        }
                    }
                }.onClick { viewPosX, viewPosY, relativeX, relativeY, x, y ->
                    println("Clicked")
                }.onDrag { viewPosX, viewPosY, relativeX, relativeY, x, y ->
                    posX -= relativePosX - relativeX
                    posY -= relativePosY - relativeY
                }.onHover { viewPosX, viewPosY, relativeX, relativeY, x, y ->
                    hovered = true
                    relativePosX = relativeX
                    relativePosY = relativeY
                }
            }
        }.onHover { viewPosX, viewPosY, relativeX, relativeY, x, y ->
            hovered = false
        }
    }

    KtxUIFrame(screen)
}