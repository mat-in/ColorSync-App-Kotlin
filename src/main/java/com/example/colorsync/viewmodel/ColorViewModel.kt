package com.example.colorsync.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsync.data.ColorDao
import com.example.colorsync.data.ColorEntry
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ColorViewModel(private val colorDao: ColorDao) : ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://colorsync-81550-default-rtdb.firebaseio.com/")

    private val _colorList = MutableStateFlow<List<ColorEntry>>(emptyList())
    val colorList: StateFlow<List<ColorEntry>> = _colorList

    private val _pendingSyncCount = MutableStateFlow(0)
    val pendingSyncCount: StateFlow<Int> = _pendingSyncCount

    init {
        viewModelScope.launch {
            colorDao.getAllColors().collect { colors ->
                _colorList.value = colors
                _pendingSyncCount.value = colors.size
            }
        }
    }

    fun addColor() {
        viewModelScope.launch {
            val colorEntry = ColorEntry(
                colorHex = generateRandomColor(),
                createdAt = System.currentTimeMillis()
            )
            colorDao.insertColor(colorEntry)
            _pendingSyncCount.value += 1
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            val colorList = colorDao.getAllColors().first() // Get all colorsfrom Room
            val colorsRef = database.reference.child("colors") // Create a reference to "colors" node

            colorList.forEach { colorEntry ->
                val colorKey = colorsRef.push().key // Generate a unique key
                if (colorKey != null) {
                    colorsRef.child(colorKey).setValue(colorEntry) // Push the color data
                }
            }

            colorDao.clearColors() // Clear Room database
            _pendingSyncCount.value = 0
        }
    }

    private fun generateRandomColor(): String {
        return String.format("#%06X", (0xFFFFFF and java.util.Random().nextInt(0xFFFFFF)))
    }
}
