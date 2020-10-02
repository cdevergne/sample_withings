package com.devergne.withings.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devergne.withings.data.Image

class DetailViewModel(image : Image) : ViewModel() {
    private val _data = MutableLiveData<Image>(image)
    val image: LiveData<Image>
        get() = _data

}