package com.devergne.withings.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devergne.withings.data.Image
import com.devergne.withings.databinding.ListFragmentBinding
import com.devergne.withings.ui.MainActivity
import com.devergne.withings.ui.list.paging.ImageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val listViewModel : ListViewModel by viewModel()

    private val dataSelectionCallback = object : ImageSelectionValidationCallback {
        override fun onSelectionValidated(imageList: List<Image>) {
            (requireActivity() as MainActivity).navigateToDetail(imageList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val listFragmentBinding = ListFragmentBinding.inflate(layoutInflater)

        listViewModel.imageSelectionValidationCallback = dataSelectionCallback
        listViewModel.imageAdapter =
            ImageAdapter(this)

        listFragmentBinding.lifecycleOwner = this
        listFragmentBinding.viewModel = listViewModel

        return listFragmentBinding.root
    }
}