package com.test.orangeocssample.ui.searchschedules

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.test.orangeocssample.domaine.Schedule

/**
 * Adapter for our list of schedules
 */
class SchedulesAdapter : ListAdapter<Schedule, ScheduleViewHolder>(SCHEDULE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleContentItem = getItem(position)
        if (scheduleContentItem != null) {
            holder.bind(
                Schedule(
                    scheduleContentItem.id,
                    scheduleContentItem.title,
                    scheduleContentItem.subtitle,
                    scheduleContentItem.imageUrl
                )
            )
        }
    }

    companion object {
        private val SCHEDULE_COMPARATOR = object : DiffUtil.ItemCallback<Schedule>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
                oldItem == newItem
        }
    }
}