package com.camg_apps.memory_butts

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.camg_apps.memory_butts.databinding.DialogUpdateAppBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class UpdateAppDialogFragment(private val update: () -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogUpdateAppBinding.inflate(layoutInflater)

        binding.btnUpdate.setOnClickListener {update()}

        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        ).apply {
            setView(binding.root)
            isCancelable = false
        }.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

}