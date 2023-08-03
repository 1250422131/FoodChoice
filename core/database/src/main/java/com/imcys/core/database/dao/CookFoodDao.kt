package com.imcys.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.imcys.core.database.entity.CookFoodEntity

@Dao
interface CookFoodDao {
    @Query("SELECT * from fc_cook_food where stuff = :stuff ORDER BY id DESC")
    suspend fun selectByStuffList(stuff: String): MutableList<CookFoodEntity>

    @Query("SELECT * from fc_cook_food where name = :name")
    suspend fun selectByName(name: String): CookFoodEntity?

    @Query("SELECT * from fc_cook_food where bv = :bvId")
    suspend fun selectByBvId(bvId: String): CookFoodEntity?

    @Query("SELECT * from fc_cook_food ORDER BY id DESC")
    suspend fun selectList(): MutableList<CookFoodEntity>

    @Insert
    suspend fun inserts(vararg cookFoodEntity: CookFoodEntity)

    @Update
    suspend fun update(cookFoodEntity: CookFoodEntity)
}
