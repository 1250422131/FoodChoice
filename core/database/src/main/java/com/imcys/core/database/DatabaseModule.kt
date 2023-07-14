package com.imcys.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DB_NAME = "db_food_choice"

    @Provides
    @Singleton
    fun providesFdDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME,
    ) // 是否允许在主线程进行查询
        .allowMainThreadQueries()
        // 数据库升级异常之后的回滚
        .fallbackToDestructiveMigration().build()
}
