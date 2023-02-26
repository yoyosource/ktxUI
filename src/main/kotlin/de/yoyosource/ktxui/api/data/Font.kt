package de.yoyosource.ktxui.api.data

import de.yoyosource.ktxui.api.protocols.*

fun interface Font {
    fun invoke(fontAPI: FontProtocol<*, *>)
}

val largeTitle = Font {
    it.design(FontFamily("Helvetica"))
    it.size(34f)
    it.weight(Bold)
    it.bold()
}

val title = Font {
    it.design(FontFamily("Helvetica"))
    it.size(28f)
    it.weight(Bold)
}

val title2 = Font {
    it.design(FontFamily("Helvetica"))
    it.size(22f)
    it.weight(Regular)
}

val title3 = Font {
    it.design(FontFamily("Helvetica"))
    it.size(20f)
    it.weight(Regular)
}

val headline = Font {
    it.design(FontFamily("Helvetica"))
    it.size(17f)
    it.weight(Regular)
}

val subheadline = Font {
    it.design(FontFamily("Helvetica"))
    it.size(15f)
    it.weight(Regular)
}

val body = Font {
    it.design(FontFamily("Helvetica"))
    it.size(17f)
    it.weight(Regular)
}

val callout = Font {
    it.design(FontFamily("Helvetica"))
    it.size(16f)
    it.weight(Regular)
}

val caption = Font {
    it.design(FontFamily("Helvetica"))
    it.size(12f)
    it.weight(Regular)
}

val caption2 = Font {
    it.design(FontFamily("Helvetica"))
    it.size(11f)
    it.weight(Regular)
}

val footnote = Font {
    it.design(FontFamily("Helvetica"))
    it.size(13f)
    it.weight(Regular)
}
