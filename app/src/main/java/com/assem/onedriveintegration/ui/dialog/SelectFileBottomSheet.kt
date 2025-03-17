package com.assem.onedriveintegration.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.assem.onedriveintegration.databinding.BottomsheetSelectFileBinding
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.ui.OneDriveViewModel
import com.assem.onedriveintegration.ui.base.BaseBottomSheet
import com.assem.onedriveintegration.ui.dialog.adapter.FilesAdapter
import com.assem.onedriveintegration.utils.Resource
import com.assem.onedriveintegration.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SelectFileBottomSheet() :
    BaseBottomSheet<BottomsheetSelectFileBinding>(BottomsheetSelectFileBinding::inflate) {

    private val viewModel: OneDriveViewModel by activityViewModels()

    private val filesAdapter by lazy {
        FilesAdapter(::onFileClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserFiles()
        initViews()
        observeUserFiles()
    }

    private fun initViews() {
        binding.run {
            rvFiles.adapter = filesAdapter
        }
    }

    private fun observeUserFiles() {
        viewModel.userOneDriveFiles.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    requireContext().showToast(it.error ?: "")
                }

                is Resource.Loading -> {
                    // TODO -> show loading
                }

                is Resource.Success -> {
                    it.data?.let { data ->
                        if (data.isEmpty()) {
                            requireContext().showToast("Data is empty!")
                        } else {
                            filesAdapter.submitList(data)
                        }
                    }
                }
            }
        }
    }

    private fun onFileClicked(item: OneDriveItem) {
        Log.d("Assem", "onFileClicked: " + item.name)
        dismissAllowingStateLoss()
    }
}