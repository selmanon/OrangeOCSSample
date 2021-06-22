package com.test.orangeocssample.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.TITLE_QUALIFIER
import retrofit2.HttpException
import java.io.IOException

class OcsPagingSource(private val ocsService: OcsService, private val query: String) :
    PagingSource<Int, ScheduleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScheduleResponse> {
        val position = params.key ?: OCS_STARTING_PAGE_INDEX
        val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else OCS_STARTING_PAGE_INDEX
        val apiQuery = TITLE_QUALIFIER + query

        return try {
            val response =
                ocsService.searchSchedules(apiQuery, offset = offset, limit = params.loadSize)
            val contents = response.results

            val nextKey = if (contents.isNullOrEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = if (contents.isNullOrEmpty()) emptyList() else contents,
                prevKey = null,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, ScheduleResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val OCS_STARTING_PAGE_INDEX = 1
        private const val NETWORK_PAGE_SIZE = 30
    }
}