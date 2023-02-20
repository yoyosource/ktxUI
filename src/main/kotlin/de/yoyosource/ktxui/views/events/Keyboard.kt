package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.ViewElement
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ModifierKey
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Keyboard(): Keyboard {
    return +KeyboardImpl()
}

sealed interface Keyboard {
    fun press(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onPress(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard

    fun release(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onRelease(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard

    fun type(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onType(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard
}

class KeyboardImpl : ViewElement(), Keyboard {

    private var pressAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }
    private var releaseAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }
    private var typeAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
    }

    override fun press(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        pressAction(keyCode, keyChar, modifier)
    }

    override fun onPress(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        pressAction = action
        return this
    }

    override fun release(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        releaseAction(keyCode, keyChar, modifier)
    }

    override fun onRelease(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        releaseAction = action
        return this
    }

    override fun type(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        typeAction(keyCode, keyChar, modifier)
    }

    override fun onType(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        typeAction = action
        return this
    }
}