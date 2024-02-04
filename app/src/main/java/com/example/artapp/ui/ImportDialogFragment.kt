package com.example.artapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.artapp.R
import com.example.artapp.viewmodel.ChallengeListViewModel
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class ImportDialogFragment(private val challengeListViewModel: ChallengeListViewModel): DialogFragment() {
    private lateinit var filePath: Uri

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_import, null)
            val importStringField = view.findViewById<EditText>(R.id.import_string_field)
            val importFileField = view.findViewById<EditText>(R.id.import_file_field)
            val importFileButton = view.findViewById<ImageButton>(R.id.import_file_button)
            val importModeField = view.findViewById<Spinner>(R.id.import_mode_spinner)
            val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                val fileNameIndex = uri?.path?.lastIndexOf('/')
                println(uri?.path)
                if (fileNameIndex != null && fileNameIndex != -1)
                    importFileField.setText(uri?.path?.substring(fileNameIndex + 1))
                filePath = uri ?: Uri.EMPTY
            }

            importFileButton.setOnClickListener {
                getContent.launch("application/json")
            }
            builder
                .setView(view)
                .setTitle(R.string.dialog_import_title)
                .setPositiveButton(R.string.dialog_import_positive) { _, _ ->
                    var text = importStringField.text.toString()
                    if (text.isBlank())
                        text = getFileContent(filePath)
                    val mode = importModeField.selectedItem.toString()
                    importChallenges(text, mode)
                }
                .setNegativeButton(R.string.dialog_import_negative) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun importChallenges(text: String, mode: String)
    {
        when (mode) {
            getString(R.string.import_mode_add) -> challengeListViewModel.import(text)
            getString(R.string.import_mode_replace_duplicate) -> challengeListViewModel.import(text, true)
            getString(R.string.import_mode_full_replace) -> challengeListViewModel.clearAndImport(text)
        }
    }

    private fun getFileContent(uri: Uri): String {
        try {
            var result = ""
            var str: String? = ""
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            if (inputStream != null)
            {
                val buffer = BufferedReader(InputStreamReader(inputStream))
                try {
                    while (str != null) {
                       result += str
                       str = buffer.readLine()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    inputStream.close()
                }
                return result
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}