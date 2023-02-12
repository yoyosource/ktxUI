package de.yoyosource

import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.impl.Graphics2dDrawable
import de.yoyosource.ktxui.views.*
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

var testText by Observer("Hello World")

fun main() {
    var screen = Screen {
        VStack {
            Spacer()
            Text("Hello")
            VStack {
                Spacer()
                Text("Hello\n\nWorld")
                Spacer()
            }
            Text("World")
            Spacer()
        }
    }

    screen = Screen {
        VBottom {
            HCenter {
                ZStack {
                    HCenter {
                        Text("Hello World")
                    }
                    HCenter {
                        Text("____________")
                            .color(Color.RED)
                    }
                }
            }
            HCenter {
                Text(::testText)
            }
        }
    }

    val image = BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics() as Graphics2D
    val drawable = Graphics2dDrawable(g, 128, 128)

    drawable.draw(screen)
    screen.addRedrawListener {
        println("Redraw")
        drawable.draw(screen)
    }
    testText = "Hello World 2"

    ImageIO.write(image, "png", File("test.png"))
}