package com.devergne.withings.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devergne.withings.databinding.DetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()
    private val detailViewModel : DetailViewModel by viewModel { parametersOf(args.imageList.toList()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val detailFragmentBinding = DetailFragmentBinding.inflate(inflater)

        detailFragmentBinding.lifecycleOwner = this
        detailFragmentBinding.viewModel = detailViewModel

        return detailFragmentBinding.root
    }
}