
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
package org.aerovek.chartr.ui.account

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.aerovek.chartr.R
import org.aerovek.chartr.databinding.CreateAccountFragmentBinding
import org.aerovek.chartr.ui.BaseFragment
import org.aerovek.chartr.ui.media.MediaChooserDismissListener
import org.aerovek.chartr.ui.media.MediaChooserFragment
import org.aerovek.chartr.util.DialogModel
import org.aerovek.chartr.util.showGenericDialog
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateAccountFragment : BaseFragment() {
    private val viewModel: CreateAccountViewModel by inject()
    private lateinit var binding: CreateAccountFragmentBinding
    private lateinit var permissionsLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var currentPhotoPath: String
    private var currentPhotoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted && permissionsGranted()) {
                showBottomSheet()
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let { intent ->
                intent.extras?.let { bundle ->
                    val bitmap = bundle.get("data") as Bitmap

                    currentPhotoFile?.let {
                        viewModel.profileImageFile = it
                    }

                    viewModel.profileImageBitmap.postValue(bitmap)
                }
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let { uri ->
                println("GALLERY IMAGE URI: ${uri.path}")

                val bitmap = if (Build.VERSION.SDK_INT < 29) {
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }

                var newBitmap = Bitmap.createBitmap(bitmap)
                newBitmap = newBitmap.copy(Bitmap.Config.ARGB_8888, true)

                // Create a new file so we can rename it
                val originalFile = File(uri.path!!)
                val newFile = try {
                    createImageFile()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                    null
                }

                originalFile.renameTo(newFile!!)
                println("New Filname: ${newFile.name}")

                viewModel.profileImageFile = newFile
                viewModel.profileImageBitmap.postValue(newBitmap)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<CreateAccountFragmentBinding>(inflater, R.layout.create_account_fragment, container, false).apply {
            binding = this
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            setupBackPressListener { findNavController().navigateUp() }

            val menuHost: MenuHost = requireActivity()