package com.test.orangeocssample.ui.detailschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.test.orangeocssample.databinding.FragmentScheduleDetailsBinding
import com.test.orangeocssample.ui.searchschedules.ScheduleViewHolder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "title"
private const val ARG_SUBTITLE = "subtitle"
private const val ARG_PITCH = "pitch"
private const val ARG_IMAGE_URL = "imageurl"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleDetailsFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDetailsBinding

    private var title: String? = null
    private var subtitle: String? = null
    private var pitch: String? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            subtitle = it.getString(ARG_SUBTITLE)
            pitch = it.getString(ARG_PITCH)
            imageUrl = it.getString(ARG_IMAGE_URL)
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
        binding.itemScheduleDetailsTitle.text = title
        binding.itemScheduleDetailsSubTitle.text = subtitle
        binding.textViewPitch.text = pitch
        Glide
            .with(binding.imageViewSchedule.context)
            .load(ScheduleViewHolder.IMAGE_BASE_URL + imageUrl)
            //.placeholder() TODO
            .into(binding.imageViewSchedule)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, param1)
                    putString(ARG_SUBTITLE, param2)
                }
            }
    }
}