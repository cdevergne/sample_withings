package com.devergne.withings.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devergne.withings.BR
import com.devergne.withings.R
import com.devergne.withings.data.Image
import com.devergne.withings.data.repository.ImageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber

class ListViewModel(private val dataRepository: ImageRepository) : ViewModel() {

    val itemBinding =
        ItemBinding.of<ImageViewModel>(BR.viewModel, R.layout.image_row_item)

    private val _dataViewModelList = MutableLiveData<List<ImageViewModel>>()
    val dataViewModelList: LiveData<List<ImageViewModel>>
        get() = _dataViewModelList

    private val _state = MutableLiveData<State>(State.LOADING)
    val state: LiveData<State>
        get() = _state


    private var disposable: Disposable? = null

    var dataSelectionCallback: DataSelectionCallback? = null
    private val localDataSelectionCallback = object : DataSelectionCallback {
        override fun onDataSelected(image: Image) {
            dataSelectionCallback?.onDataSelected(image)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun refreshList() {
        reduceEvent(Event.DataListLoading)

        disposable?.dispose()
        disposable = dataRepository.getAllImage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { reduceEvent(Event.DataListLoadSuccess(it)) },
                {
                    Timber.e("Error while refreshing list\n$it")
                    reduceEvent(Event.DataListLoadFailure)
                }
            )
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
/*                _dataViewModelList.postValue(event.dataList.map { data ->
                    DataViewModel(
                        data,
                        localDataSelectionCallback
                    )
                })*/

                _dataViewModelList.value = event.imageList.map { data ->
                    ImageViewModel(
                        data,
                        localDataSelectionCallback
                    )
                }
            }
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