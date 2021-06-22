package com.test.orangeocssample.data

import androidx.paging.PagingSource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OcsPagingSourceTest {

    private val DEFAULT_QUERY: String = "game"

    private val scheduleFactory = ScheduleFactory()
    private val mockSchedules = listOf(
        scheduleFactory.createSchedule(),
        scheduleFactory.createSchedule()
    )

    private val mockApi = MockOcsService()

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runBlockingTest {
        val pagingSource = OcsPagingSource(mockApi, DEFAULT_QUERY)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(mockSchedules[0]),
                prevKey = null,
                nextKey = 1
            ),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }
}