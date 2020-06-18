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
package org.aerovek.chartr.ui.more

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.aerovek.chartr.R
import org.aerovek.chartr.databinding.MoreFragmentBinding
import org.aerovek.chartr.ui.AppConstants
import org.aerovek.chartr.ui.BaseFragment
import org.aerovek.chartr.ui.adapterItems.MoreItem
import org.aerovek.chartr.ui.adapterItems.viewmodels.MoreItemViewModel
import org.aerovek.chartr.util.DialogModel
import org.aerovek.chartr.util.NavigationObserver
import org.aerovek.chartr.util.setDataItems
import org.aerovek.chartr.util.showGenericDialog
import org.koin.android.ext.android.inject

class MoreFragment : BaseFragment() {
    private val viewModel: MoreViewModel by inject()
    private lateinit var binding: MoreFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<MoreFragmentBinding>(inflater, R.layout.more_fragment, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner

            setupBackPressListener {
                findNavController().popBackStack(R.id.homeFragment, false)
            }

            val settingsItem = MoreItem(requireContext(),
                MoreItemViewModel(
                    MoreItemType.Settings,
                    getString(R.string.settings_title),
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_settin