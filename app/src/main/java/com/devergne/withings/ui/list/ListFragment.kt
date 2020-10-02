package com.devergne.withings.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devergne.withings.data.Image
import com.devergne.withings.databinding.ListFragmentBinding
import com.devergne.withings.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val listViewModel : ListViewModel by viewModel()

    private val dataSelectionCallback = object : ImageSelectionCallback {
        override fun onDataSelected(image: Image) {
            (requireActivity() as MainActivity).navigateToDetail(image)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val listFragmentBinding = ListFragmentBinding.inflate(layoutInflater)

        listViewModel.imageSelectionCallback = dataSelectionCallback

        listFragmentBinding.lifecycleOwner = this
        listFragmentBinding.viewModel = listViewModel

        return listFragmentBinding.root
    }
}