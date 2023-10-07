package com.imcys.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "fc_cook_food",
)
data class CookFoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "bv")
    val bv: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "difficulty")
    val difficulty: String,
    @ColumnInfo(name = "methods")
    val methods: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "stuff")
    var stuff: String, // 腊肠、米
    @ColumnInfo(name = "tags")
    val tags: String, // 广式
    @ColumnInfo(name = "tools")
    var tools: String, // 电饭煲
    @ColumnInfo(name = "url")
    var url: String, // 电饭煲
) {
    @Ignore
    var emoji: String = "" // 电饭煲

    @Ignore
    var image: String = "" // 电饭煲
}
