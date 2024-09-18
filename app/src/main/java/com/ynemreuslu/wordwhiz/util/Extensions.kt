package com.ynemreuslu.wordwhiz.util

import android.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showCustomAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    onPositiveAction: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    onNegativeAction: (() -> Unit)? = null
) {
    val dialogBuilder = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
            onPositiveAction?.invoke()
            dialogInterface.dismiss()
        }

    negativeButtonText?.let {
        dialogBuilder.setNegativeButton(it) { dialogInterface, _ ->
            onNegativeAction?.invoke()
            dialogInterface.dismiss()
        }
    }

    val dialog = dialogBuilder.create()
    dialog.show()
}