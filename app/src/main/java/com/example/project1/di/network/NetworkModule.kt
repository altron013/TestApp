package com.example.project1.di.network

import com.example.project1.network.api.FeedApi
import com.example.project1.network.repository.FeedsRepository
import com.example.project1.network.repository.FeedsRepositoryImpl
import com.example.project1.utilities.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single(named("base_url")) { BaseUrlProvider() }
    single { HttpLoggingInterceptorProvider() }
    single { StethoInterceptorProvider() }
    single { AuthInterceptorProvider(apiKey = "") }
    single {
        OkHttpProvider(
            loggingInterceptor = get(),
            stethoInterceptor = get(),
            authInterceptor = get()
        )
    }
    single { CallAdapterFactoryProvider() }
    single { ConverterFactoryProvider() }
    single {
        RetrofitProvider(
            baseUrl = get(named("base_url")),
            okHttpClient = get(),
            gsonConverterFactory = get(),
            callAdapterFactory = get()
        )
    }
    single { ApiProvider(retrofit = get()) }
}

val repositoryModule = module {
    single { RepositoryProvider(feedApi = get()) }
}
fun AuthInterceptorProvider(apiKey: String): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}
fun ApiProvider(retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

fun BaseUrlProvider(): String = AppConstants.BASE_URL

fun HttpLoggingInterceptorProvider(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun StethoInterceptorProvider(): StethoInterceptor = StethoInterceptor()

fun OkHttpProvider(
    loggingInterceptor: HttpLoggingInterceptor,
    stethoInterceptor: StethoInterceptor,
    authInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(stethoInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun CallAdapterFactoryProvider(): CallAdapter.Factory = CoroutineCallAdapterFactory()

fun ConverterFactoryProvider(): Converter.Factory = GsonConverterFactory.create()

fun RetrofitProvider(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}

fun RepositoryProvider(feedApi: FeedApi): FeedsRepository = FeedsRepositoryImpl(feedApi)
