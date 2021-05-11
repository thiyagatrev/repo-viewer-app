package com.example.repoviewer.di

import android.content.Context
import com.example.repoviewer.BuildConfig
import com.example.repoviewer.api.ApiCalls
import com.example.repoviewer.api.NewNetworkModule
import com.example.repoviewer.api.apibuilder.RetrofitApiBuilder
import com.example.repoviewer.database.RoomInitializer
import com.example.repoviewer.repositary.ApiRespositary
import com.example.repoviewer.repositary.RepoCommentRepositaryImpl
import com.example.repoviewer.repositary.RepoCommentRepositaryUseCase
import com.example.repoviewer.usecase.GetPublicRepoUsecase
import com.example.repoviewer.usecase.GetPublicRepoUsecaseImpl
import com.example.repoviewer.viewmodel.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


object KoinUtils {

    @ExperimentalCoroutinesApi
    fun setupKoin(context: Context, list: List<Module>) {
        startKoin {
            androidContext(androidContext = context)
            modules(list)

            apply {
                if (BuildConfig.DEBUG) {
                    this.printLogger()
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    val injectionModule = module {

        factory { NewNetworkModule.providesBaseUrl() }
        factory { NewNetworkModule.providesRetrofitBuilder() }
        factory { NewNetworkModule.providesHttpLoggingInterceptor() }
        factory { NewNetworkModule.providesGson() }
        factory { NewNetworkModule.providesNetworkInterceptor() }
        factory { NewNetworkModule.providesOkHttpClientBuilder() }
        factory { NewNetworkModule.providesOkhttpClientWithAuthentication(get(), get(), get()) }
        factory {
            NewNetworkModule.providesRetrofitWithoutAuthentication(
                get(),
                get(),
                get(),
                get()
            )
        }

        single<ApiCalls> {
            RetrofitApiBuilder(
                NewNetworkModule.providesRetrofitBuilder(),
                NewNetworkModule.providesGson(),
                NewNetworkModule.providesOkhttpClient(get(), get(), get())
            ).build(NewNetworkModule.providesBaseUrl())
        }




        factory<GetPublicRepoUsecase> {
            GetPublicRepoUsecaseImpl(get())
        }

        viewModel {
            SharedViewModel(get(), get())
        }
        factory {
            ApiRespositary(get())
        }

        single { RoomInitializer.getInstance() }

        factory<RepoCommentRepositaryUseCase> { RepoCommentRepositaryImpl(get()) }

    }

    @JvmStatic
    fun <T : Any> get(clazz: Class<T>): T {
        val kClass = clazz.kotlin
        return getKoin().get(
            kClass,
            null,
            null
        )
    }

    private fun getKoin(): Koin = GlobalContext.get()

}