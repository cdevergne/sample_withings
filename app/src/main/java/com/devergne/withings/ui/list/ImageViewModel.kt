package com.devergne.withings.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devergne.withings.data.Image

/**
 * Didn't extends android ViewModel to prevent leak.
 * I didn't find a rapid way to customize lifecycle owner.
 * This ViewModel should not live as long as the fragment but as long as the research display
 */
class ImageViewModel(val image : Image) {

    private val _selected = MutableLiveData<Boolean>(false)
    val selected : LiveData<Boolean>
    get() = _selected

    fun toggleSelect() {
        _selected.postValue(!(selected.value ?: false))
    }
}