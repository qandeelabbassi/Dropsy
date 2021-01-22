package com.qandeelabbassi.dropsy

/**
 * Created by qandeel.rasheed on 1/19/2021 at 11:56 PM.
 */
class DropDownItem(var text: String, var checked: Boolean = false) {
    fun toggleState() {
        checked = !checked
    }
}