package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.Observer
import kotlin.reflect.KProperty0

private interface IntIntermediary {
    var value: Int
}

operator fun KProperty0<Int>.plus(other: Int): KProperty0<Int> {
    return object : IntIntermediary {
        override var value: Int by Observer(this@plus) { it + other }
    }::value
}

operator fun KProperty0<Int>.minus(other: Int): KProperty0<Int> {
    return object : IntIntermediary {
        override var value: Int by Observer(this@minus) { it - other }
    }::value
}

operator fun KProperty0<Int>.times(other: Int): KProperty0<Int> {
    return object : IntIntermediary {
        override var value: Int by Observer(this@times) { it * other }
    }::value
}

operator fun KProperty0<Int>.div(other: Int): KProperty0<Int> {
    return object : IntIntermediary {
        override var value: Int by Observer(this@div) { it / other }
    }::value
}