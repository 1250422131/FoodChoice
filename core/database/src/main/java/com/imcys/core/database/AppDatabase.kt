package com.imcys.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imcys.core.database.dao.CookFoodDao
import com.imcys.core.database.dao.CookingIngredientDao
import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.database.entity.CookingIngredientEntity

@Database(
    entities = [CookingIngredientEntity::class, CookFoodEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cookingIngredientDao(): CookingIngredientDao

    abstract fun cookFoodDao(): CookFoodDao
}
