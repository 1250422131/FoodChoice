package com.imcys.core.common.data

interface IBaseRepository {
    suspend fun syncWithData(): Boolean
}