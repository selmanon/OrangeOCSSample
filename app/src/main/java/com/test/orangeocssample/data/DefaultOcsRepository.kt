package com.test.orangeocssample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultOcsRepository(private val ocsService: OcsService, private val mapper: ScheduleMapper) :
    OcsRepository {
    override fun getSchedulesStreamBy(title: String): Flow<PagingData<Schedule>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { OcsPagingSource(ocsService, title) }
        )

            .flow
            .map {
                it.map { scheduleResponse: ScheduleResponse ->
                    mapper.mapToDomain(scheduleResponse)
                }
            }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}