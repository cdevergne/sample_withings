package com.devergne.withings.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devergne.withings.data.Image
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var detailViewModel: DetailViewModel

    @Test
    fun `test viewmodel use first image by default`(){
        val img1 = mockk<Image>()
        val img2 = mockk<Image>()
        val img3 = mockk<Image>()
        detailViewModel = DetailViewModel(listOf(img1, img2, img3))

        assertEquals(img1, detailViewModel.image.value)
    }

    @Test
    fun `test next select next image`()
    {
        val img1 = mockk<Image>()
        val img2 = mockk<Image>()
        val img3 = mockk<Image>()
        detailViewModel = DetailViewModel(listOf(img1, img2, img3))

        detailViewModel.nextImage()
        assertEquals(img2, detailViewModel.image.value)
        detailViewModel.nextImage()
        assertEquals(img3, detailViewModel.image.value)
    }

    @Test
    fun `test next cycle`()
    {
        val img1 = mockk<Image>()
        val img2 = mockk<Image>()
        val img3 = mockk<Image>()
        detailViewModel = DetailViewModel(listOf(img1, img2, img3))

        detailViewModel.nextImage()
        detailViewModel.nextImage()
        detailViewModel.nextImage()
        assertEquals(img1, detailViewModel.image.value)
    }
}