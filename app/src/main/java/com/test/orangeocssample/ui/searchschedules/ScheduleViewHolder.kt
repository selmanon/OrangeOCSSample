package com.test.orangeocssample.ui.searchschedules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.orangeocssample.R
import com.test.orangeocssample.databinding.ScheduleViewItemBinding
import com.test.orangeocssample.domaine.Schedule

/**
 * View Holder for a [Content] RecycleView list item.
 */
class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var mSchedule: Schedule? = null
    private val textViewTitle: TextView = view.findViewById(R.id.textView_title)
    private val textViewSubTitle: TextView = view.findViewById(R.id.textView_sub_title)
    private val imageViewSchedule: ImageView = view.findViewById(R.id.imageView_schedule)


    init {
        view.setOnClickListener {
        }
    }

    fun bind(schedule: Schedule?) {
        if (schedule == null) {
            // ViewHolder must support binding null item as placeholder because an item may be null
            val resource = itemView.resources
            textViewTitle.text = resource.getString(R.string.unkown)
            textViewSubTitle.text = resource.getString(R.string.unkown)
            // TODO imageView
        } else {
            this.mSchedule = schedule
            textViewTitle.text = schedule.title
            textViewSubTitle.text = schedule.subtitle
            Glide
                .with(itemView.context)
                .load(IMAGE_BASE_URL + schedule.imageUrl)
                //.placeholder() TODO
                .into(imageViewSchedule)
        }
    }

    companion object {
        const val IMAGE_BASE_URL = "https://statics.ocs.fr"

        fun create(parent: ViewGroup): ScheduleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.schedule_view_item, parent, false)
            return ScheduleViewHolder(view)
        }
    }
}
