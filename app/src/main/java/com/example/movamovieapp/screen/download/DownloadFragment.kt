package com.example.movamovieapp.screen.download

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.widget.addTextChangedListener

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.DownloadAdapter
import com.example.movamovieapp.databinding.FragmentDownloadBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadFragment : BaseFragment<FragmentDownloadBinding>(FragmentDownloadBinding::inflate) {

    private val viewModel: DownloadViewModel by viewModels()

    private val downloadadapter = DownloadAdapter()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDownloads()
        observe()
        binding.rvdownload.adapter = downloadadapter

        binding.imageViewsearch.setOnClickListener {
            if (binding.searchLayout.visibility == View.VISIBLE) {
                binding.searchLayout.gone()
                binding.imageView63.visible()
                binding.textView71.visible()
            } else {
                binding.searchLayout.visible()
                binding.imageView63.gone()
                binding.textView71.gone()
            }
        }

        binding.searchedittext.addTextChangedListener { text ->
            val query = text.toString().trim()
            lifecycleScope.launch {
                delay(300)
                if (query.isNotEmpty()) {
                    viewModel.searchDownload(query)
                } else {
                    viewModel.getDownloads()
                }
            }
        }







        downloadadapter.onItemClick = {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                val action =
                    DownloadFragmentDirections.actionDownloadFragment2ToDetailFragment(it.id)
                findNavController().navigate(action)
                return@launch
                binding.animationView22.gone()
            }
        }

        downloadadapter.onDeleteClick = { download ->
            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .create()

            val titleTextView = dialogView.findViewById<TextView>(R.id.tvTitle)
            val messageTextView = dialogView.findViewById<TextView>(R.id.tvMessage)
            val noButton = dialogView.findViewById<TextView>(R.id.btnNo)
            val yesButton = dialogView.findViewById<TextView>(R.id.btnYes)

            titleTextView.text = "Delete Download"
            messageTextView.text = "Are you sure you want to delete this download?"

            noButton.setOnClickListener {
                dialog.dismiss()
            }
            yesButton.setOnClickListener {

                viewModel.deleteDownload(download.id)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }
            dialog.show()

        }
    }


    private fun observe() {
        viewModel.downloads.observe(viewLifecycleOwner) {
            downloadadapter.updateList(it)
            if (it.isEmpty()) {
                binding.textViewNoresult.visible()
            } else {
                binding.textViewNoresult.gone()
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    }


}