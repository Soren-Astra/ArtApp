package com.example.artapp.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.artapp.ArtApplication
import com.example.artapp.R
import com.example.artapp.databinding.ActivitySettingsBinding
import com.example.artapp.viewmodel.SettingsViewModel
import com.example.artapp.viewmodel.SettingsViewModelFactory
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class SettingsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySettingsBinding
    private lateinit var filePath: Uri
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((application as ArtApplication).challengeRepository, (application as ArtApplication).promptRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setupImport()
    }

    private fun setupImport() {
        settingsViewModel.importFinishedCommand = ::importFinishedCommand
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val fileNameIndex = uri?.path?.lastIndexOf('/')
            println(uri?.path)
            if (fileNameIndex != null && fileNameIndex != -1)
                _binding.importFileField.setText(uri.path?.substring(fileNameIndex + 1))
            filePath = uri ?: Uri.EMPTY
        }

        _binding.importFileButton.setOnClickListener {
            getContent.launch("application/json")
        }
        _binding.settingsImportButton.setOnClickListener {
            var text = _binding.importStringField.text.toString()
            if (text.isBlank())
                text = getFileContent(filePath)
            val mode = _binding.importModeSpinner.selectedItem.toString()
            importChallenges(text, mode)
        }
    }

    private fun importChallenges(text: String, mode: String)
    {
        when (mode) {
            getString(R.string.import_mode_add) -> settingsViewModel.import(text)
            getString(R.string.import_mode_replace_duplicate) -> settingsViewModel.import(text, true)
            getString(R.string.import_mode_full_replace) -> settingsViewModel.clearAndImport(text)
        }
    }

    private fun getFileContent(uri: Uri): String {
        try {
            var result = ""
            var str: String? = ""
            val inputStream = contentResolver.openInputStream(uri)
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

    private fun importFinishedCommand() {
        finish()
    }
}