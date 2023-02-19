package de.yoyosource.onlyconnect

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.DebugMode
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.views.*
import java.awt.Color

var mainMenu by Observer(false)
var gameMenu by Observer(false)
var wallMenu by Observer(true)

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
    frame.enableDebug(DebugMode.SIZE)
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

fun ViewContainer.GameTile(backgroundColor: Color, builder: ViewContainer.() -> Unit) {
    AbsoluteSize(400, 225) {
        Background(backgroundColor) {
            HCenter {
                VCenter {
                    builder()
                }
            }
        }
    }
}

fun ViewContainer.Tile(backgroundColor: Color, builder: ViewContainer.() -> Unit) {
    AbsoluteSize(225, 225) {
        Background(backgroundColor) {
            HCenter {
                VCenter {
                    builder()
                }
            }
        }
    }
}