package com.test.orangeocssample.data.api

import com.test.orangeocssample.data.api.dto.details.ScheduleDetails
import com.test.orangeocssample.data.api.dto.search.ScheduleResponse
import com.test.orangeocssample.data.api.dto.search.ScheduleSearchResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val TITLE_QUALIFIER = "title="

/**
 * OCS API communication
 */


interface OcsService {
    /**
     * @return A [ScheduleResponse] that match the specified keyword.
     *
     * @param title the title of the schedule to look for.
     * @param offset the base index of the search request.
     * @param limit the max schedule items to return.
     */
    @GET("apps/v2/contents")
    fun searchSchedules(
        @Query("search") title: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 30
    ): Single<ScheduleSearchResponse<ScheduleResponse>>

    /**
     * @return A [ScheduleDetails] for a specific serie or programme.
     *
     * @param detaillink the detail link of the schedule.
     */
    @GET("{detaillink}")
    fun scheduleDetails(
        @Path("detaillink", encoded = true) detaillink: String
    ): Single<ScheduleDetails>


    companion object {
        private const val BASE_URL = "https://api.ocs.fr"

        fun create(): OcsService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(OcsService::class.java)
        }
    }
}