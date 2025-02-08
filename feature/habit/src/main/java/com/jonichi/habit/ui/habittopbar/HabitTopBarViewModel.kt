package com.jonichi.habit.ui.habittopbar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HabitTopBarViewModel @Inject constructor() : ViewModel() {
    private val _topBarState = MutableStateFlow(TopBarState())
    val topBarState = _topBarState.asStateFlow()


    fun updateTopBar(title: String, onBackAction: (() -> Unit)? = null) {
        _topBarState.update { state ->
            state.copy(title = title, onBackAction = onBackAction)
        }
    }
}
