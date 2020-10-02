package com.devergne.withings.di

import com.devergne.withings.data.Image
import com.devergne.withings.data.repository.ImageRepository
import com.devergne.withings.data.repository.remote.PixabayDataRepository
import com.devergne.withings.data.repository.remote.PixabayImageConverter
import com.devergne.withings.ui.detail.DetailViewModel
import com.devergne.withings.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sampleModule = module {
//    single { FakeDataRepository() as DataRepository }
    single { PixabayDataRepository(PixabayImageConverter()) as ImageRepository }

    viewModel { ListViewModel(get())}
    viewModel { (image: Image) -> DetailViewModel(image) }
}