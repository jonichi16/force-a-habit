package com.jonichi.habit.ui.habittopbar

data class TopBarState(
    val title: String = "",
    val onBackAction: (() -> Unit)? = null
) {
}