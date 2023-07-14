package com.imcys.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.imcys.core.database.entity.CookingIngredientEntity

@Dao
interface CookingIngredientDao {
    @Query("SELECT * from fc_cooking_ingredient where type = :type ORDER BY id DESC")
    suspend fun selectByTypeList(type: Int): MutableList<CookingIngredientEntity>

    @Query("SELECT * from fc_cooking_ingredient ORDER BY id DESC")
    suspend fun selectList(): MutableList<CookingIngredientEntity>

    @Query("SELECT * from fc_cooking_ingredient where name = :name ORDER BY id DESC")
    suspend fun selectByName(name: String): CookingIngredientEntity?

    @Insert
    suspend fun inserts(vararg cookingIngredientEntity: CookingIngredientEntity)

    @Update
    suspend fun update(cookingIngredientEntity: CookingIngredientEntity)
}
