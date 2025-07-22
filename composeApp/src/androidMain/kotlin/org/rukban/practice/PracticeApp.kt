package org.rukban.practice

import org.rukban.practice.screens.ListViewModel
import android.app.Application
import org.rukban.practice.di.initKoin
import org.rukban.practice.screens.DetailViewModel
import org.koin.dsl.module

class PracticeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        initKoin(
            listOf(
                module {
                    factory { ListViewModel(get()) }
                    factory { DetailViewModel(get()) }
                }
            )
        )
    }
}