package com.example.quicksellapp.modules

import com.example.quicksellapp.BuildConfig
import com.example.quicksellapp.Constants
import com.example.quicksellapp.repositories.IProductRepository
import com.example.quicksellapp.repositories.MockApiRepositoryImpl
import com.example.quicksellapp.retrofit.IMockApiRetrofitService
import com.example.quicksellapp.retrofit.MockApiRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProductRepository(mockApiRetrofitService: IMockApiRetrofitService): IProductRepository {
        return MockApiRepositoryImpl(mockApiRetrofitService)
    }

    @Singleton
    @Provides
    fun provideMockApiRetrofit(mockApiRetrofitService: MockApiRetrofitService.IMockApiService): IMockApiRetrofitService {
        return MockApiRetrofitService(mockApiRetrofitService)
    }

    @Singleton
    @Provides
    fun provideMockApiRetrofitInstance(retrofit: Retrofit): MockApiRetrofitService.IMockApiService {
        return retrofit.create(MockApiRetrofitService.IMockApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        val instagramApiUrl = URL(Constants.MOCK_API_URL)
        return Retrofit.Builder()
            .client(createHttpClient())
            .baseUrl(instagramApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        lateinit var okHttpClient: OkHttpClient
        val builder: OkHttpClient.Builder = if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logging)
        } else {
            OkHttpClient.Builder()
        }.readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        okHttpClient = builder.build()

        return okHttpClient
    }

}