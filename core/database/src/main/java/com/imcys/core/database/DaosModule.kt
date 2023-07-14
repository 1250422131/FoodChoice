package com.imcys.core.database

import com.imcys.core.database.dao.CookFoodDao
import com.imcys.core.database.dao.CookingIngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesCookingIngredientDao(
        database: AppDatabase,
    ): CookingIngredientDao = database.cookingIngredientDao()

    @Provides
    fun providesCookFoodDao(
        database: AppDatabase,
    ): CookFoodDao = database.cookFoodDao()
}
