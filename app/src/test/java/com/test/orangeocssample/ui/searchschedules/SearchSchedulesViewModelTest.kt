package com.test.orangeocssample.ui.searchschedules

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.orangeocssample.data.SearchScheduleInteractor
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class SearchSchedulesViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cut: SearchSchedulesViewModel

    @Mock
    private lateinit var observer: Observer<SearchSchedulesViewModel.UiState>

    @Mock
    private lateinit var searchInteractor: SearchScheduleInteractor

    @Captor
    lateinit var captor: ArgumentCaptor<SearchSchedulesViewModel.UiState>

    private val testScheduler = TestScheduler()

    lateinit var scheduls: List<Schedule>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        cut = SearchSchedulesViewModel(searchInteractor, testSchedulerProvider)
        scheduls = listOf(
            Schedule(
                "",
                "",
                "",
                "",
            )
        )
    }

    @Test
    fun `search success`() {
        // Arrange
        `when`(searchInteractor.getSchedule("", 1)).thenReturn(Single.just(scheduls))


        val expectedResult = MutableLiveData<SearchSchedulesViewModel.UiState>()
        expectedResult.value = SearchSchedulesViewModel.UiState.Success(scheduls)

        cut.searchState.observeForever(observer)

        try {
            // Act
            cut.searchSchedules("", 1)
            testScheduler.triggerActions()

            // Assert
            captor.run {
                verify(observer, times(1)).onChanged(capture())
                assertEquals(expectedResult.value, cut.searchState.value)
            }

        } finally {
            cut.searchState.removeObserver(observer)
        }
    }

    @Test
    fun `search fail`() {
        // Arrange
        `when`(searchInteractor.getSchedule("", 1)).thenReturn(Single.error(Throwable("")))


        val expectedResult = MutableLiveData<SearchSchedulesViewModel.UiState>()
        expectedResult.value = SearchSchedulesViewModel.UiState.Error("")

        cut.searchState.observeForever(observer)

        try {
            // Act
            cut.searchSchedules("", 1)
            testScheduler.triggerActions()

            // Assert
            captor.run {
                verify(observer, times(1)).onChanged(capture())
                assertEquals(expectedResult.value, cut.searchState.value)
            }

        } finally {
            cut.searchState.removeObserver(observer)
        }
    }

}