package com.devergne.withings.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devergne.withings.BR
import com.devergne.withings.R
import com.devergne.withings.data.Image
import com.devergne.withings.data.paging.ImagePagingRepository
import com.devergne.withings.data.repository.ImageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber

class ListViewModel(private val dataRepository: ImageRepository, private val imagePagingRepository: ImagePagingRepository) : ViewModel() {

    val itemBinding =
        ItemBinding.of<ImageViewModel>(BR.viewModel, R.layout.image_item)

    private val _dataViewModelList = MutableLiveData<List<ImageViewModel>>()
    val dataViewModelList: LiveData<List<ImageViewModel>>
        get() = _dataViewModelList

    private val _state = MutableLiveData<State>(State.LOADING)
    val state: LiveData<State>
        get() = _state


    private var disposable: Disposable? = null

    var imageSelectionCallback: ImageSelectionCallback? = null

    val searchText = MutableLiveData<String>()

    val imageAdapter = ImageAdapter()

    private var searchJob: Job? = null

    init {
        //refreshList()
        initStateListener()
        search()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    private fun initStateListener() {
        imageAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Error -> reduceEvent(Event.DataListLoadFailure)
                is LoadState.Loading -> reduceEvent(Event.DataListLoading)
                is LoadState.NotLoading -> reduceEvent(Event.DataListLoadSuccess(listOf()))
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
            imagePagingRepository.getImagesStream().collect {
                imageAdapter.submitData(it)
            }
        }
    }


    fun refreshList() {
/*        reduceEvent(Event.DataListLoading)

        disposable?.dispose()
        disposable = dataRepository.getImageWithFilter("aa", 0, 150)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { reduceEvent(Event.DataListLoadSuccess(it)) },
                {
                    Timber.e("Error while refreshing list\n$it")
                    reduceEvent(Event.DataListLoadFailure)
                }
            )*/
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.DataListLoading -> {
                _state.postValue(State.LOADING)
                _dataViewModelList.postValue(listOf())
            }
            is Event.DataListLoadFailure -> {
                _state.postValue(State.ERROR)
                _dataViewModelList.postValue(listOf())
            }
            is Event.DataListLoadSuccess -> {
                _state.postValue(State.DISPLAY)
              /*  _dataViewModelList.value = event.imageList.map { data ->
                    ImageViewModel(
                        data
                    )
                }*/
            }
        }
    }

    fun validateSelection() {
        val selectedImage =
            _dataViewModelList.value?.filter { it.selected.value == true }?.map { it.image }
        selectedImage?.let {
            if (it.size >= 2)
                imageSelectionCallback?.onSelectionValidated(it)
        }
    }

    enum class State {
        DISPLAY,
        ERROR,
        LOADING
    }

    private sealed class Event {
        class DataListLoadSuccess(val imageList: List<Image>) : Event()
        object DataListLoadFailure : Event()
        object DataListLoading : Event()
    }
}