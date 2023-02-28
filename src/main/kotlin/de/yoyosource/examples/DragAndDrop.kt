package de.yoyosource.examples

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.api.views.Image
import de.yoyosource.ktxui.api.views.ImageJarResource
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.Text
import de.yoyosource.ktxui.api.views.layout.ZStack
import de.yoyosource.ktxui.api.views.special.Conditional
import de.yoyosource.ktxui.views.events.onClick
import de.yoyosource.ktxui.views.events.onDrag
import de.yoyosource.ktxui.views.events.onHover
import de.yoyosource.ktxui.views.position.AbsolutePosition
import de.yoyosource.ktxui.views.position.RelativePosition

private var posX by Observer(0)
private var posY by Observer(0)

private var hovered by Observer(false)
private var relativePosX by Observer(0)
private var relativePosY by Observer(0)

private var image by ImageJarResource("/test.png")

fun main() {
    val screen = Screen {
        AbsolutePosition(::posX, ::posY) {
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
                if (relativeX > 128 || relativeY > 128) {
                    hovered = false
                    return@onHover
                }
                hovered = true
                relativePosX = relativeX
                relativePosY = relativeY
            }
        }
    }.onHoverNothing {
        hovered = false
    }

    KtxUIFrame(screen)
}