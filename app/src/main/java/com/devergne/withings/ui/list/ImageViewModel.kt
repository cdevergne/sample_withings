package com.devergne.withings.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devergne.withings.data.Image

class ImageViewModel(val image : Image) {

    private val _selected = MutableLiveData<Boolean>(false)
    val selected : LiveData<Boolean>
    get() = _selected

    fun toggleSelect() {
        _selected.postValue(!(selected.value ?: false))
    }
}