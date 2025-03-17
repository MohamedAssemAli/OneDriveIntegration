package com.assem.onedriveintegration.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.assem.onedriveintegration.databinding.ActivityMainBinding
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.ui.base.BaseActivity
import com.assem.onedriveintegration.ui.dialog.SelectFileBottomSheet
import com.assem.onedriveintegration.ui.dialog.adapter.FilesAdapter
import com.assem.onedriveintegration.utils.Resource
import com.assem.onedriveintegration.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @get:Inject
    val viewModel: OneDriveViewModel by viewModels()

    private val filesAdapter by lazy { FilesAdapter(::onFileClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserFiles()
        initViews()
        observeUserFiles()
    }

    private fun initViews() {
        binding.run {
            rvSelectFiles.adapter = filesAdapter
            fab.setOnClickListener {
                val selectFileDialog = SelectFileBottomSheet()
                selectFileDialog.show(supportFragmentManager, "SelectFoodBottomSheet")
            }
        }
    }

    private fun onFileClicked(item: OneDriveItem) {
        Log.d("Assem", "onFileClicked: " + item.name)
    }

    private fun observeUserFiles() {
        viewModel.userOneDriveFiles.observe(this) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.error ?: "")
                }

                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    it.data?.let { data ->
                        if (data.isEmpty()) {
                            showToast("Data is empty!")
                        } else {
                            filesAdapter.submitList(data)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.run {
            progressBar.isVisible = isLoading
            rvSelectFiles.isVisible = isLoading.not()
        }
    }
}