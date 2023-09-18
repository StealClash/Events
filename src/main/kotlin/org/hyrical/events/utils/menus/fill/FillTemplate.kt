package org.hyrical.events.utils.menus.fill

import org.hyrical.events.utils.menus.fill.impl.BorderFiller
import org.hyrical.events.utils.menus.fill.impl.FillFiller

enum class FillTemplate(val menuFiller: IMenuFiller? = null) {
    FILL(FillFiller()), BORDER(BorderFiller());

}