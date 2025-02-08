package com.jonichi.habit.ui.habittopbar

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class HabitTopBarTest {
    private lateinit var viewModel: HabitTopBarViewModel

    @Before
    fun setUp() {
        viewModel = HabitTopBarViewModel()
    }

    @Test
    fun `topBarState should be able to update the topBar`() {
        val state = viewModel.topBarState.value
        assertEquals(TopBarState(), state)

        viewModel.updateTopBar(title = "Home")
        val updated = viewModel.topBarState.value
        assertEquals(TopBarState(title = "Home"), updated)
    }
}