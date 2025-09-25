package com.example.movamovieapp.screen.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.MainActivity
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.CommentAdapter
import com.example.movamovieapp.adapters.CreditsAdapter
import com.example.movamovieapp.adapters.MoreLikeAdapter
import com.example.movamovieapp.adapters.TrailerAdapter
import com.example.movamovieapp.databinding.FragmentDetailBinding
import com.example.movamovieapp.model.DownloadModel
import com.example.movamovieapp.model.MyListModel
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.tabs.TabLayout

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()

    private val creditsAdapter = CreditsAdapter()
    private val commentAdapter = CommentAdapter()
    private val moreLikeAdapter = MoreLikeAdapter()
    private val trailerAdapter = TrailerAdapter()
    private var isSelected = false

    private var canNavigate = true


    private var downloadDialog: AlertDialog? = null
    private var downloadJob: Job? = null




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvtrailer.adapter = trailerAdapter
        binding.rvlikemore.adapter = moreLikeAdapter
        binding.rvcomment.adapter = commentAdapter
        binding.recyclerView3.adapter = creditsAdapter

        trailerAdapter.onItemClick = { video ->
            openYoutube(requireContext(), video.key)
        }

        binding.buttonplay.setOnClickListener {
            val trailers = viewModel.trailer.value
            if (!trailers.isNullOrEmpty()) {

                val officialTrailer = trailers.find { it.type == "Trailer" && it.official }
                val trailerToPlay = officialTrailer ?: trailers[0]
                openYoutube(requireContext(), trailerToPlay.key)
            } else {
                Toast.makeText(requireContext(), "No trailer found", Toast.LENGTH_SHORT).show()
            }
        }



        moreLikeAdapter.onItemClickListener = {
            val action = DetailFragmentDirections.actionDetailFragmentSelf(it.id)
            findNavController().navigate(action)

        }
        binding.buttonDetailDownload.setOnClickListener {

            downloadDialog(requireContext())

        }



        binding.imageView8back.setOnClickListener {
            findNavController().popBackStack()
        }

        setupTabLayout()
        observeData()

        viewModel.getMovieDetail(args.id)
        viewModel.getMovieCredits(args.id)
        viewModel.getComments(args.id)
        viewModel.getmore()
        viewModel.getMovieTrailers(args.id)
        binding.rvtrailer.visible()
        binding.rvlikemore.gone()
        binding.rvcomment.gone()

        setupAddButton()
    }

    private fun safeNavigate(action: NavDirections) {
        if (canNavigate) {
            canNavigate = false
            findNavController().navigate(action)
            viewLifecycleOwner.lifecycleScope.launch {
                delay(500)
                canNavigate = true
            }
        }
    }


    private fun downloadDialog(context: Context) {
        if (downloadDialog?.isShowing == true) return

        val download = DownloadModel(
            title = viewModel.detail.value?.title ?: "No Title",
            image = viewModel.detail.value?.posterPath ?: "",
            id = viewModel.detail.value?.id ?: 0
        )

        viewLifecycleOwner.lifecycleScope.launch {
            val alreadyAdded = withContext(Dispatchers.IO) { viewModel.isDownloadAdded(download.id) }
            if (alreadyAdded) {
                Toast.makeText(requireContext(), "Already Added", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_download, null)
            val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)
            val progressText = dialogView.findViewById<TextView>(R.id.progressText)
            val hideButton = dialogView.findViewById<Button>(R.id.hideButton)
            val statusText = dialogView.findViewById<TextView>(R.id.statusText)

            downloadDialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create()
            downloadDialog?.show()

            val downloadJob = viewLifecycleOwner.lifecycleScope.launch {
                for (i in 1..100) {
                    if (!isActive) break
                    delay(30)
                    progressBar.progress = i
                    progressText.text = "$i%"
                    statusText.text = if (i < 100) "Downloading..." else "Download Complete"
                }

                if (isActive && downloadDialog?.isShowing == true) {
                    withContext(Dispatchers.IO) { viewModel.addDownload(download) }
                    Toast.makeText(requireContext(), "Download Added", Toast.LENGTH_SHORT).show()
                    downloadDialog?.dismiss()
                }
            }

            hideButton.setOnClickListener {
                downloadJob.cancel()
                downloadDialog?.dismiss()
                Toast.makeText(context, "Download Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.rvtrailer.visible()
                        binding.rvlikemore.gone()
                        binding.rvcomment.gone()

                    }

                    1 -> {
                        binding.rvtrailer.gone()
                        binding.rvlikemore.visible()
                        binding.rvcomment.gone()
                    }

                    2 -> {
                        binding.rvtrailer.gone()
                        binding.rvlikemore.gone()
                        binding.rvcomment.visible()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.tabLayout.getTabAt(0)?.select()
    }

    private fun openYoutube(context: Context, youtubeKey: String?) {
        val youtubeAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeKey"))
        val youtubeWebIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$youtubeKey"))

        try {
            context.startActivity(youtubeAppIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(youtubeWebIntent)
        }
    }


    private fun observeData() {
        viewModel.detail.observe(viewLifecycleOwner) { detail ->
            if (!isAdded) return@observe
            binding.detail = detail
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (!isAdded) return@observe
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        }

        viewModel.credits.observe(viewLifecycleOwner) {
            if (!isAdded) return@observe
            binding.recyclerView3.post {
                creditsAdapter.updateCredits(it.cast)

            }
        }

        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            if (!isAdded) return@observe
            binding.rvcomment.post {
                commentAdapter.updateList(comments)
            }

            viewModel.more.observe(viewLifecycleOwner) {  moreMovies ->
                if (!isAdded) return@observe
                binding.rvlikemore.post {
                    moreLikeAdapter.updateMore(moreMovies)
                }
            }

            viewModel.trailer.observe(viewLifecycleOwner) {trailers ->
                if (!isAdded) return@observe
                binding.rvtrailer.post {
                    trailerAdapter.updateList(trailers)

                }
            }

        }
    }


    private fun setupAddButton() {
        viewLifecycleOwner.lifecycleScope.launch {
            val added = viewModel.isMovieAdded(args.id)
            isSelected = added
            updateButtonUI()
        }

        binding.imageviewbuttonadd.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.detail.value?.let { movieDetail ->
                    val movieItem = MyListModel(
                        title = movieDetail.title ?: "No Title",
                        image = movieDetail.posterPath ?: "",
                        selected = !isSelected,
                        id = movieDetail.id ?: 0
                    )

                    if (isSelected) {
                        viewModel.deleteMovie(movieItem.id)
                        isSelected = false
                        Toast.makeText(requireContext(), "Movie Removed", Toast.LENGTH_SHORT).show()
                        updateButtonUI()
                    } else {
                        viewModel.addMovie(movieItem)
                        isSelected = true
                        Toast.makeText(requireContext(), "Movie Added", Toast.LENGTH_SHORT).show()
                        updateButtonUI()
                    }

                }

            }
        }

    }

    private fun updateButtonUI() {
        val image = if (isSelected) R.drawable.selectlist else R.drawable.defaultlist
        binding.imageviewbuttonadd.setImageResource(image)
    }

}
