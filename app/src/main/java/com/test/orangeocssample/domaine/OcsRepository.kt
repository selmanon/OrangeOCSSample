package com.test.orangeocssample.domaine

import androidx.paging.PagingData
import com.test.orangeocssample.data.api.Result
import kotlinx.coroutines.flow.Flow

interface OcsRepository {
    fun getSchedulesStreamBy(title: String): Flow<PagingData<Schedule>>
}