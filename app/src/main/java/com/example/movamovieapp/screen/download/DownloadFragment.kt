package com.example.movamovieapp.screen.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.DownloadAdapter
import com.example.movamovieapp.databinding.FragmentDownloadBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : BaseFragment<FragmentDownloadBinding>(FragmentDownloadBinding::inflate) {

    private val viewModel: DownloadViewModel by viewModels()

    private val downloadadapter = DownloadAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvdownload.adapter = downloadadapter
        viewModel.getDownloads()
        observe()


        downloadadapter.onItemClickListener = {
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
                viewModel.deleteDownload(it.id)
                dialog.dismiss()
            }
            dialog.show()

        }

    }

    private fun observe() {
        viewModel.downloads.observe(viewLifecycleOwner) {
            downloadadapter.updateList(it)
        }
    }


}

