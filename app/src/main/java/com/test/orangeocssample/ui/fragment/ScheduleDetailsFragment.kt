package com.test.orangeocssample.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.videoplayerlib.PlayerActivity
import com.bumptech.glide.Glide
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.interactor.ScheduleDetailsInteractor
import com.test.orangeocssample.data.mapper.ScheduleDetailsMapper
import com.test.orangeocssample.data.repository.DefaultScheduleDetailsRepository
import com.test.orangeocssample.databinding.FragmentScheduleDetailsBinding
import com.test.orangeocssample.ui.adapter.ScheduleViewHolder
import com.test.orangeocssample.ui.searchschedules.ScheduleDetailsViewModel
import com.test.orangeocssample.ui.searchschedules.ScheduleDetailsViewModelFactory
import com.test.orangeocssample.utils.rxjava.DefaultScheduler
import java.net.UnknownHostException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "title"
private const val ARG_SUBTITLE = "subtitle"
private const val ARG_PITCH = "pitch"
private const val ARG_IMAGE_URL = "fullimageurl"

class ScheduleDetailsFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDetailsBinding
    private lateinit var scheduleDetailsViewModel: ScheduleDetailsViewModel

    private var title: String? = null
    private var subtitle: String? = null
    private var pitch: String = ""
    private var fullImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            subtitle = it.getString(ARG_SUBTITLE)
            pitch = it.getString(ARG_PITCH) ?: ""
            fullImageUrl = it.getString(ARG_IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observeScheduleDetailsViewModel()

        binding.itemScheduleDetailsTitle.text = title
        binding.itemScheduleDetailsSubTitle.text = subtitle
        Glide
            .with(binding.imageViewSchedule.context)
            .load(ScheduleViewHolder.IMAGE_BASE_URL + fullImageUrl)
            //.placeholder() TODO
            .into(binding.imageViewSchedule)

        if (pitch.isNotEmpty()) {
            scheduleDetailsViewModel.getScheduleDetails(pitch)
        } else {
            binding.itemScheduleDetailsPitch.visibility = View.GONE
        }

        binding.imageViewPlay.setOnClickListener {
            startActivity(Intent(requireActivity(), PlayerActivity::class.java))
        }
    }

    private fun observeScheduleDetailsViewModel() {
        scheduleDetailsViewModel.scheduleDetailsState.observe(requireActivity(), {
            when (it) {
                is ScheduleDetailsViewModel.UiState.Error -> {
                    if (it.throwable is UnknownHostException) {
                        Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is ScheduleDetailsViewModel.UiState.Success -> {
                    binding.itemScheduleDetailsPitch.text = it.data.pitch
                }
            }
        })
    }

    private fun initViewModel() {
        // create our view model
        val scheduleDetailsViewModelFactory = ScheduleDetailsViewModelFactory(
            ScheduleDetailsInteractor(
                createScheduleDetailsRepository()
            ), DefaultScheduler()
        )
        scheduleDetailsViewModel =
            ViewModelProvider(
                this,
                scheduleDetailsViewModelFactory
            ).get(ScheduleDetailsViewModel::class.java)
    }

    private fun createScheduleDetailsRepository(): DefaultScheduleDetailsRepository =
        DefaultScheduleDetailsRepository(
            OcsService.create(), ScheduleDetailsMapper()
        )
}