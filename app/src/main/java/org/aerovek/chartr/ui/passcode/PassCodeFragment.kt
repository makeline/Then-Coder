
/*
The MIT License (MIT)

Copyright (c) 2023-present Aerovek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.aerovek.chartr.ui.passcode

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.aerovek.chartr.R
import org.aerovek.chartr.databinding.PasscodeFragmentBinding
import org.aerovek.chartr.ui.adapterItems.PassCodeEntryItem
import org.aerovek.chartr.ui.adapterItems.PassCodeKeyboardItem
import org.aerovek.chartr.util.DialogModel
import org.aerovek.chartr.util.makeBottomsheetFullScreen
import org.aerovek.chartr.util.setDataItems
import org.aerovek.chartr.util.showGenericDialog
import org.koin.android.ext.android.inject

class PassCodeFragment : BottomSheetDialogFragment() {

    private val GRID_COLUMN_COUNT_DOTS = 6
    private val GRID_COLUMN_COUNT_KEYPAD = 3
    private val passCodeViewModel: PassCodeViewModel by inject()
    private lateinit var binding: PasscodeFragmentBinding
    private lateinit var pinItems : MutableList<PassCodeEntryItem>
    private lateinit var pinNumbers : List<PassCodeKeyboardItem>
    private var dismissListener: PassCodeDismissListener? = null
    private var isDismissable: Boolean = false
    private var isValidPin = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<PasscodeFragmentBinding>(inflater, R.layout.passcode_fragment, container, false).apply {

            binding = this
            viewModel = passCodeViewModel
            lifecycleOwner = this@PassCodeFragment.viewLifecycleOwner
            setupRecyclerView(binding.recycleButtonView , GRID_COLUMN_COUNT_DOTS)
            setupRecyclerView(binding.recycleKeyPadView , GRID_COLUMN_COUNT_KEYPAD)

            pinItems = mutableListOf(
                PassCodeEntryItem(0 , false),
                PassCodeEntryItem(1 , false),
                PassCodeEntryItem(2 , false),
                PassCodeEntryItem(3 , false),
                PassCodeEntryItem(4 , false),
                PassCodeEntryItem(5 , false)
            )

            binding.recycleButtonView.apply {
                setDataItems(pinItems)
            }

            passCodeViewModel.insecurePinDetected.observe(viewLifecycleOwner) {
                showGenericDialog(
                    requireContext(),
                    DialogModel(
                        R.string.insecure_pin_title,
                        R.string.insecure_pin_desc
                    ), dismissListener = null
                )
            }

            passCodeViewModel.clearDots.observe(viewLifecycleOwner) {
                pinItems.forEach {
                    it.isActive = false
                    binding.recycleButtonView.adapter?.notifyItemChanged(pinItems.indexOf(it))
                }
            }
