package com.lagradost.cloudstream3.activation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lagradost.cloudstream3.R
import kotlinx.coroutines.launch

class ActivationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_activation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val codeInput = view.findViewById<EditText>(R.id.activation_code_input)
        val activateButton = view.findViewById<Button>(R.id.activation_button)
        val progressBar = view.findViewById<ProgressBar>(R.id.activation_progress)
        val errorText = view.findViewById<TextView>(R.id.activation_error)

        // Auto-uppercase as user types
        codeInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val upper = s.toString().uppercase()
                if (upper != s.toString()) {
                    codeInput.removeTextChangedListener(this)
                    codeInput.setText(upper)
                    codeInput.setSelection(upper.length)
                    codeInput.addTextChangedListener(this)
                }
            }
        })

        activateButton.setOnClickListener {
            val code = codeInput.text.toString().trim()
            if (code.isEmpty()) {
                errorText.text = "Please enter your activation code."
                errorText.isVisible = true
                return@setOnClickListener
            }

            // Show loading state
            progressBar.isVisible = true
            activateButton.isEnabled = false
            errorText.isVisible = false

            lifecycleScope.launch {
                try {
                    val response = ActivationHelper.activate(requireContext(), code)
                    if (response.success && response.token != null) {
                        ActivationHelper.saveActivation(response)
                        // Navigate to the normal setup wizard
                        findNavController().navigate(R.id.action_activation_to_setup_language)
                    } else {
                        errorText.text = ActivationHelper.mapErrorMessage(response.error)
                        errorText.isVisible = true
                    }
                } catch (e: Exception) {
                    errorText.text = "Connection failed. Check your internet connection."
                    errorText.isVisible = true
                } finally {
                    progressBar.isVisible = false
                    activateButton.isEnabled = true
                }
            }
        }
    }
}
