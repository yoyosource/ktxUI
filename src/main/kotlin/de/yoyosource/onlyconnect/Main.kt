package de.yoyosource.onlyconnect

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.DebugMode
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.utils.ViewBuilder
import de.yoyosource.ktxui.views.*
import de.yoyosource.ktxui.views.events.Button
import de.yoyosource.ktxui.views.shapes.RoundedRectangle
import de.yoyosource.ktxui.views.size.AbsoluteSize
import de.yoyosource.ktxui.views.size.FitSize
import java.awt.Color

var mainMenu by Observer(true)
var gameMenu by Observer(false)
var wallMenu by Observer(false)

private val backgroundColor = Color.PINK

fun main() {
    val screen = Screen {
        ZStack {
            Conditional(::mainMenu) {
                MainMenu()
            }
            Conditional(::gameMenu) {
                GameMenu()
            }
            Conditional(::wallMenu) {
                WallMenu()
            }
        }
    }

    val frame = KtxUIFrame(screen)
    if (false) {
        frame.enableDebug(DebugMode.SIZE)
    }
}

fun ViewContainer.MainMenu() {
    HCenter {
        VCenter {
            Tile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer()
            Tile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
        }
        Spacer()
        VCenter {
            Tile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer()
            Tile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
        Spacer()
        VCenter {
            Tile(backgroundColor) {
                Text("--- 5 ---")
                    .size(20)
            }
            Spacer()
            Tile(backgroundColor) {
                Text("--- 6 ---")
                    .size(20)
            }
        }
    }
}

fun ViewContainer.GameMenu() {
    VCenter {
        HCenter {
            GameTile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer(5)
            GameTile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
            Spacer(5)
            GameTile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer(5)
            GameTile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
    }
}

fun ViewContainer.WallMenu() {
    HCenter {
        VCenter {
            Tile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
        Spacer(5)
        VCenter {
            Tile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
        Spacer(5)
        VCenter {
            Tile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
        Spacer(5)
        VCenter {
            Tile(backgroundColor) {
                Text("--- 1 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 2 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 3 ---")
                    .size(20)
            }
            Spacer(5)
            Tile(backgroundColor) {
                Text("--- 4 ---")
                    .size(20)
            }
        }
    }
}

fun ViewContainer.GameTile(backgroundColor: Color, builder: ViewBuilder) {
    AbsoluteSize(400, 225) {
        FitSize { width, height ->
            ZStack {
                RoundedRectangle()
                    .color(backgroundColor)
                    .width(width)
                    .height(height)
                HCenter {
                    VCenter {
                        builder()
                    }
                }
            }
        }
    }
}

fun ViewContainer.Tile(backgroundColor: Color, builder: ViewBuilder): Button {
    return Button {
        AbsoluteSize(225, 225) {
            FitSize { width, height ->
                ZStack {
                    RoundedRectangle()
                        .color(backgroundColor)
                        .width(width)
                        .height(height)
                    HCenter {
                        VCenter {
                            builder()
                        }
                    }
                }
            }
        }
    }
}