package com.example.colorsync.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val colorHex: String,
    val createdAt: Long
) {
    fun getFormattedDate(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(createdAt))
    }
}
