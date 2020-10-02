package com.devergne.withings.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devergne.withings.data.Image

class DetailViewModel(private val imageList : List<Image>) : ViewModel() {

    private var indexDisplay = 0
    private val _image = MutableLiveData<Image>(imageList[indexDisplay])

    val image: LiveData<Image>
        get() = _image

    fun nextImage() {
        indexDisplay = (indexDisplay + 1)  % imageList.size
        _image.postValue(imageList[indexDisplay])
    }
}