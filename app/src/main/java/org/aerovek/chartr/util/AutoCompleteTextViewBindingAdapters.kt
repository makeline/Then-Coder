package org.aerovek.chartr.util

import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import org.aerovek.chartr.R

// Need both to be handled in same function as it can happen that the initialSelection is called prior to setting the adapter
@BindingAdapter(value = ["entries", "initialSelection"], requireAll = false)
fun <T : Any> AutoCompleteTextView.setAdapterWithInitialSelection(entries: List<T>?, position: Int?) {
    if (entries != null && position != null) {
        setAdapterWithInitialSelectionWorker(entries, position)
    }
}

// Need both to be handled in same function as it can happen that the initialSelection is called prior to setting the adapter
@BindingAdapter(value = ["entries", "