package com.example.quicksellapp.viewmodels

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quicksellapp.FakeConstants
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.repositories.MockRepositoryFakeImpl
import com.example.quicksellapp.screens.quicksell.QuickSellViewModel
import com.example.quicksellapp.services.MockApiFakeRetrofitService
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class QuickSellViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var quickSellViewModel: QuickSellViewModel
    private lateinit var fakeMockRepository: Field

    @Before
    fun setupViewModel() {
        quickSellViewModel = QuickSellViewModel()
        fakeMockRepository = quickSellViewModel.javaClass.getDeclaredField("repository")
        fakeMockRepository.isAccessible = true
        fakeMockRepository.set(quickSellViewModel, MockRepositoryFakeImpl())
    }

    @Test
    fun onGetPostsSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        MockApiFakeRetrofitService.mockData = FakeConstants.mockApiResponse
        MockApiFakeRetrofitService.responseCode = 200

        quickSellViewModel.retrieveProducts()

        val value = quickSellViewModel.onGetProducts.value
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun onGetPostsFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        MockApiFakeRetrofitService.responseCode = 400

        quickSellViewModel.retrieveProducts()

        val error = quickSellViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
    }
}