package com.example.colorsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsync.data.AppDatabase
import com.example.colorsync.ui.theme.ColorAppScreen
import com.example.colorsync.viewmodel.ColorViewModel
import com.example.colorsync.viewmodel.ColorViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = AppDatabase.getDatabase(this)
            val factory = ColorViewModelFactory(database.colorDao())
            val viewModel: ColorViewModel = viewModel(factory = factory)

            val colorList = viewModel.colorList.collectAsState()
            val pendingSyncCount = viewModel.pendingSyncCount.collectAsState()

            ColorAppScreen(
                colorList = colorList.value,
                pendingSyncCount = pendingSyncCount.value,
                onAddColorClick = { viewModel.addColor() },
                onSyncClick = { viewModel.syncColors() }
            )
        }
    }
}
