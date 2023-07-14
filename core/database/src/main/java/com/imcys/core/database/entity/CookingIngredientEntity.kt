package com.imcys.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "fc_cooking_ingredient",
)
data class CookingIngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "emoji")
    var emoji: String,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "alias")
    var alias: String? = null,
    @ColumnInfo(name = "label")
    var label: String? = null,
    @ColumnInfo(name = "type")
    var type: Int = VEGETABLE, // 1蔬菜水果 2肉类 3工具
) {
    companion object {
        const val VEGETABLE = 1
        const val MEAT = 2
        const val TOOL = 3
        const val STAPLE = 4 // 主食
    }
}
