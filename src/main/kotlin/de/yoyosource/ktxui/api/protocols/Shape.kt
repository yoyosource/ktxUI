package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.Protocol

interface ShapeProtocol<A> : Protocol<A>, ForegroundColorProtocol<A> where A : ShapeProtocol<A>
