package com.devergne.withings.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devergne.withings.common.RxImmediateSchedulerRule
import com.devergne.withings.data.repository.ImageRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.*
import org.junit.Assert.assertEquals

class ListViewModelTest {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var imageRepository: ImageRepository

    private lateinit var listViewModel: ListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { imageRepository.getImageWithFilter(any()) } returns Single.just(listOf())
        listViewModel = ListViewModel(imageRepository)
        clearMocks(imageRepository)
    }

    @Test
    fun `test refresh escalate call to repository`() {

        every { imageRepository.getImageWithFilter(any()) } returns Single.just(listOf())

        listViewModel.refreshList()

        verify(exactly = 1) { imageRepository.getImageWithFilter(any()) }
    }

    @Test
    fun `test wrapping model in viewModel`() {
        every { imageRepository.getImageWithFilter(any()) } returns Single.just(
            listOf(
                mockk(),
                mockk(),
                mockk()
            )
        )

        listViewModel.refreshList()

        assertEquals(3, listViewModel.dataViewModelList.value?.size)
    }

    @Test
    fun `test error status`() {
        every { imageRepository.getImageWithFilter(any()) } returns Single.create { it.onError(RuntimeException())}

        listViewModel.refreshList()

        assertEquals(ListViewModel.State.ERROR, listViewModel.state.value)
    }

    @Test
    fun `test display status`() {
        every { imageRepository.getImageWithFilter(any()) } returns Single.just(
            listOf(
                mockk(),
                mockk(),
                mockk()
            )
        )

        listViewModel.refreshList()

        assertEquals(ListViewModel.State.DISPLAY, listViewModel.state.value)
    }
}