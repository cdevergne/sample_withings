package com.devergne.withings.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devergne.withings.ui.list.paging.ImageAdapter
import com.devergne.withings.ui.list.paging.ImagePagingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ListViewModel(
    private val imagePagingRepository: ImagePagingRepository
) : ViewModel() {
    private val _state = MutableLiveData<State>(State.LOADING)
    val state: LiveData<State>
        get() = _state

    var imageSelectionValidationCallback: ImageSelectionValidationCallback? = null

    val searchText = MutableLiveData<String>()

    var imageAdapter: ImageAdapter? = null
        set(value) {
            value?.let {
                field = it
                initStateListener()
                search()
            }
        }

    private var searchJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

    private fun initStateListener() {
        imageAdapter?.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Error -> reduceEvent(Event.DataListLoadFailure)
                is LoadState.Loading -> reduceEvent(Event.DataListLoading)
                is LoadState.NotLoading -> reduceEvent(Event.DataListLoadSuccess)
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Timber.e("Append / preprend error $it.error")
            }
        }
    }

    fun search() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            imagePagingRepository.getImagesStream(searchText.value).collect {
                imageAdapter?.submitData(it)

            }
        }
    }

    private fun reduceEvent(event: Event) {
        Timber.d("reduceEvent $event")
        when (event) {
            is Event.DataListLoading -> {
                _state.postValue(State.LOADING)
            }
            is Event.DataListLoadFailure -> {
                _state.postValue(State.ERROR)
            }
            is Event.DataListLoadSuccess -> {
                _state.postValue(State.DISPLAY)
            }
        }
    }

    fun validateSelection() {
        imageAdapter?.let { imageAdapter ->
            val selectedImage =
                imageAdapter.snapshot().items.filter { it.selected.value == true }.map { it.image }
            if (selectedImage.size >= 2)
                imageSelectionValidationCallback?.onSelectionValidated(selectedImage)
        }
    }

    enum class State {
        DISPLAY,
        ERROR,
        LOADING
    }

    private sealed class Event {
        object DataListLoadSuccess : Event()
        object DataListLoadFailure : Event()
        object DataListLoading : Event()
    }
}