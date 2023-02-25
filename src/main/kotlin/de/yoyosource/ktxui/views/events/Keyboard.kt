package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.View
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.Keyboard(): Keyboard {
    return +KeyboardImpl()
}

fun ViewContainer.Keybind(builder: KeybindBuilder.() -> Unit): Keyboard {
    val keybindBuilder = KeybindBuilder().apply(builder)
    val keyChar = if (keybindBuilder.keyChar == null) null else ViewOption(' ')
    val modifier = if (keybindBuilder.modifier == null) null else ViewOption(setOf<ModifierKey>())
    val ignoreCase = if (keybindBuilder.ignoreCase == null) null else ViewOption(false)
    return (+KeyboardImpl(keyChar, modifier, ignoreCase)).also {
        keyChar?.set(it, keybindBuilder.keyChar!!)
        modifier?.set(it, keybindBuilder.modifier!!)
        ignoreCase?.set(it, keybindBuilder.ignoreCase!!)
    }
}

class KeybindBuilder {
    internal var keyChar: Either<Char, KProperty0<Char>>? = null
    internal var modifier: Either<Set<ModifierKey>, KProperty0<Set<ModifierKey>>>? = null
    internal var ignoreCase: Either<Boolean, KProperty0<Boolean>>? = null

    fun keyChar(keyChar: Char) {
        this.keyChar = Either.Left(keyChar)
    }

    fun keyChar(keyChar: KProperty0<Char>) {
        this.keyChar = Either.Right(keyChar)
    }

    fun modifier(modifier: Set<ModifierKey>) {
        this.modifier = Either.Left(modifier)
    }

    fun modifier(modifier: KProperty0<Set<ModifierKey>>) {
        this.modifier = Either.Right(modifier)
    }

    fun ignoreCase(ignoreCase: Boolean) {
        this.ignoreCase = Either.Left(ignoreCase)
    }

    fun ignoreCase(ignoreCase: KProperty0<Boolean>) {
        this.ignoreCase = Either.Right(ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: Char, ignoreCase: Boolean = false): Keyboard {
    return +KeyboardImpl(ViewOption(keyChar), null, ViewOption(ignoreCase))
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, ignoreCase: Boolean = false): Keyboard {
    val _keyChar = ViewOption(' ')
    return (+KeyboardImpl(_keyChar, null, ViewOption(ignoreCase))).let {
        _keyChar.set(it, keyChar)
    }
}

fun ViewContainer.Keybind(keyChar: Char, ignoreCase: KProperty0<Boolean>): Keyboard {
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(ViewOption(keyChar), null, _ignoreCase)).let {
        _ignoreCase.set(it, ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, ignoreCase: KProperty0<Boolean>): Keyboard {
    val _keyChar = ViewOption(' ')
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(_keyChar, null, _ignoreCase)).let {
        _keyChar.set(it, keyChar)
        _ignoreCase.set(it, ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: Char, modifier: Set<ModifierKey>, ignoreCase: Boolean = false): Keyboard {
    return +KeyboardImpl(ViewOption(keyChar), ViewOption(modifier), ViewOption(ignoreCase))
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, modifier: Set<ModifierKey>, ignoreCase: Boolean = false): Keyboard {
    val _keyChar = ViewOption(' ')
    return (+KeyboardImpl(_keyChar, ViewOption(modifier), ViewOption(ignoreCase))).let {
        _keyChar.set(it, keyChar)
    }
}

fun ViewContainer.Keybind(keyChar: Char, modifier: KProperty0<Set<ModifierKey>>, ignoreCase: Boolean = false): Keyboard {
    val _modifier = ViewOption<Set<ModifierKey>>(emptySet())
    return (+KeyboardImpl(ViewOption(keyChar), _modifier, ViewOption(ignoreCase))).let {
        _modifier.set(it, modifier)
    }
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, modifier: KProperty0<Set<ModifierKey>>, ignoreCase: Boolean = false): Keyboard {
    val _keyChar = ViewOption(' ')
    val _modifier = ViewOption<Set<ModifierKey>>(emptySet())
    return (+KeyboardImpl(_keyChar, _modifier, ViewOption(ignoreCase))).let {
        _keyChar.set(it, keyChar)
        _modifier.set(it, modifier)
    }
}

fun ViewContainer.Keybind(keyChar: Char, modifier: Set<ModifierKey>, ignoreCase: KProperty0<Boolean>): Keyboard {
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(ViewOption(keyChar), ViewOption(modifier), _ignoreCase)).let {
        _ignoreCase.set(it, ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, modifier: Set<ModifierKey>, ignoreCase: KProperty0<Boolean>): Keyboard {
val _keyChar = ViewOption(' ')
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(_keyChar, ViewOption(modifier), _ignoreCase)).let {
        _keyChar.set(it, keyChar)
        _ignoreCase.set(it, ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: Char, modifier: KProperty0<Set<ModifierKey>>, ignoreCase: KProperty0<Boolean>): Keyboard {
    val _modifier = ViewOption<Set<ModifierKey>>(emptySet())
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(ViewOption(keyChar), _modifier, _ignoreCase)).let {
        _modifier.set(it, modifier)
        _ignoreCase.set(it, ignoreCase)
    }
}

fun ViewContainer.Keybind(keyChar: KProperty0<Char>, modifier: KProperty0<Set<ModifierKey>>, ignoreCase: KProperty0<Boolean>): Keyboard {
    val _keyChar = ViewOption(' ')
    val _modifier = ViewOption<Set<ModifierKey>>(emptySet())
    val _ignoreCase = ViewOption(false)
    return (+KeyboardImpl(_keyChar, _modifier, _ignoreCase)).let {
        _keyChar.set(it, keyChar)
        _modifier.set(it, modifier)
        _ignoreCase.set(it, ignoreCase)
    }
}

sealed interface Keyboard {
    fun press(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onPress(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard

    fun release(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onRelease(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard

    fun type(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>)
    fun onType(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard
}

private class KeyboardImpl(private val keyCharFilter: ViewOption<Char>? = null, private val keyModifierFilter: ViewOption<Set<ModifierKey>>? = null, private val ignoreCaseFilter: ViewOption<Boolean>? = null) : View(), Keyboard {

    private var pressAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }
    private var releaseAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }
    private var typeAction: (Int, Char, Set<ModifierKey>) -> Unit = { _, _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        viewState.set(this)
    }

    private fun filter(keyChar: Char, modifier: Set<ModifierKey>): Boolean {
        if (ignoreCaseFilter != null && ignoreCaseFilter.get()) {
            return (keyCharFilter == null || keyCharFilter.get().lowercaseChar() == keyChar.lowercaseChar()) && (keyModifierFilter == null || keyModifierFilter.get() == modifier)
        }
        return (keyCharFilter == null || keyCharFilter.get() == keyChar) && (keyModifierFilter == null || keyModifierFilter.get() == modifier)
    }

    override fun press(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        if (filter(keyChar, modifier))
            pressAction(keyCode, keyChar, modifier)
    }

    override fun onPress(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        pressAction = action
        return this
    }

    override fun release(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        if (filter(keyChar, modifier))
            releaseAction(keyCode, keyChar, modifier)
    }

    override fun onRelease(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        releaseAction = action
        return this
    }

    override fun type(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        if (filter(keyChar, modifier))
            typeAction(keyCode, keyChar, modifier)
    }

    override fun onType(action: (keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) -> Unit): Keyboard {
        typeAction = action
        return this
    }
}
