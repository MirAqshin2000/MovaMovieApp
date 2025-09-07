package com.example.movamovieapp.screen.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.adapters.CommentAdapter
import com.example.movamovieapp.adapters.CreditsAdapter
import com.example.movamovieapp.adapters.MoreLikeAdapter
import com.example.movamovieapp.adapters.TrailerAdapter
import com.example.movamovieapp.databinding.FragmentDetailBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.tabs.TabLayout

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()

    private val creditsAdapter = CreditsAdapter()
    private val commentAdapter = CommentAdapter()
    private val moreLikeAdapter = MoreLikeAdapter()
    private val trailerAdapter = TrailerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvtrailer.adapter = trailerAdapter
        binding.rvlikemore.adapter = moreLikeAdapter
        binding.rvcomment.adapter = commentAdapter
        binding.recyclerView3.adapter = creditsAdapter

        trailerAdapter.onItemClick = { video ->
            openYoutube(requireContext(), video.key)
        }

//        moreLikeAdapter.onItemClickListener = {
//            val action = DetailFragmentDirections.actionHomFragmentSelf(it.id)
//            findNavController().navigate(action)
//        }

//            Log.d("VideoKey",video.toString())

        binding.imageView8back.setOnClickListener {
            findNavController().popBackStack()
        }

        setupTabLayout()
        observeData()

        // Data çağırışları
        viewModel.getMovieDetail(args.id)
        viewModel.getMovieCredits(args.id)
        viewModel.getComments(args.id)
        viewModel.getmore()
        viewModel.getMovieTrailers(args.id)
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
        val youtubeWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$youtubeKey"))

        try {
            context.startActivity(youtubeAppIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(youtubeWebIntent)
        }
    }



    private fun observeData() {
        viewModel.detail.observe(viewLifecycleOwner) {
            binding.detail = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.credits.observe(viewLifecycleOwner) {
            creditsAdapter.updateCredits(it.cast)
        }

        viewModel.comments.observe(viewLifecycleOwner) {
            commentAdapter.updateList(it)
        }

        viewModel.more.observe(viewLifecycleOwner) {
            moreLikeAdapter.updateMore(it)
        }

        viewModel.trailer.observe(viewLifecycleOwner) {
            trailerAdapter.updateList(it)
        }
    }
}
