package com.assem.onedriveintegration.ui.base

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.assem.onedriveintegration.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseBottomSheet<VB : ViewBinding>(private val inflate: FragmentInflate<VB>) :
    BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!


    open var isNotDraggable = false
    open var isFullHight = true

    open var bottomSheetBehavior = BottomSheetBehavior.STATE_EXPANDED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (isFullHight || isNotDraggable) {
            val dialog = super.onCreateDialog(savedInstanceState)
            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                val bottomSheet: FrameLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                val behavior = BottomSheetBehavior.from(bottomSheet)

                if (isNotDraggable) {
                    behavior.isDraggable = false
                }
                if (isFullHight) {
                    val layoutParams: ViewGroup.LayoutParams? = bottomSheet.layoutParams
                    val windowHeight = getWindowHeight()
                    if (layoutParams != null) {
                        layoutParams.height = windowHeight
                    }
                    bottomSheet.layoutParams = layoutParams
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            return dialog
        } else {
            return super.onCreateDialog(savedInstanceState)
        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams: ViewGroup.LayoutParams? = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}