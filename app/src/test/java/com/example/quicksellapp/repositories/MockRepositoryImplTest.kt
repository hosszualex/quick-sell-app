package com.example.quicksellapp.repositories

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quicksellapp.FakeConstants
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.GetProductsResponse
import com.example.quicksellapp.model.Product
import com.example.quicksellapp.services.MockApiFakeRetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class MockRepositoryImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: IProductRepository
    private lateinit var method: Method
    private lateinit var fakeRetrofitService: Field

    @Before
    fun setupRepository() {
        mockRepository = MockApiRepositoryImpl()
        val collectionType = (typeOf<ArrayList<GetProductsResponse>>().classifier!! as KClass<ArrayList<GetProductsResponse>>).java
        method = mockRepository.javaClass.getDeclaredMethod("getProductsFromResponse", collectionType)
        method.isAccessible = true

        fakeRetrofitService = mockRepository.javaClass.getDeclaredField("retrofitService")
        fakeRetrofitService.isAccessible = true
        fakeRetrofitService.set(mockRepository, MockApiFakeRetrofitService)
    }

    @Test
    fun getOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, FakeConstants.mockApiResponse)
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun getEmptyOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, arrayListOf<GetProductsResponse>())
        Assert.assertEquals(true, (value as List<Product>).isEmpty())
    }

    @Test
    fun onGetRepositoryOrdersSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockApiFakeRetrofitService.mockData = FakeConstants.mockApiResponse
        MockApiFakeRetrofitService.responseCode = 200

        runBlocking(Dispatchers.IO) {
            mockRepository.getProducts(object: IProductRepository.IOnGetProducts{
                override fun onSuccess(products: List<Product>) {
                    Assert.assertEquals(FakeConstants.expectedMockResponse, products)
                }

                override fun onFailed(error: ErrorResponse) {}
            })
        }
    }

    @Test
    fun onGetRepositoryOrdersFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockApiFakeRetrofitService.responseCode = 400
        runBlocking(Dispatchers.IO) {
            mockRepository.getProducts(object: IProductRepository.IOnGetProducts{
                override fun onSuccess(products: List<Product>) {}

                override fun onFailed(error: ErrorResponse) {
                    Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
                }
            })
        }
    }

}