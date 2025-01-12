package com.jonichi.forceahabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jonichi.forceahabit.ui.theme.ForceAHabitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForceAHabitTheme {
                ForceAHabitApp()
            }
        }
    }
}
