package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewProtocol

interface ShapeProtocol<S, A> : ViewProtocol<S, A>, ForegroundColorProtocol<S, A> where S : ViewBase, A : ShapeProtocol<S, A>
