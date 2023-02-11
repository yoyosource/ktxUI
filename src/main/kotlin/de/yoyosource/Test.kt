package de.yoyosource

import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.Side
import de.yoyosource.ktxui.impl.Graphics2dDrawable
import de.yoyosource.ktxui.views.*
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val screen = Screen {
        VStack {
            Spacer()
            HStack {
                Padding {
                    Text("Hello")
                }
                    .padding(Side.LEFT, 10)
                Spacer()
                Padding {
                    Text("World")
                }
                    .padding(Side.RIGHT, 10)
            }
            Spacer()
        }
    }

    val image = BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics() as Graphics2D
    val drawable = Graphics2dDrawable(g, 100, 100)

    drawable.draw(screen)

    ImageIO.write(image, "png", File("test.png"))
}