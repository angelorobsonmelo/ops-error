package com.angelorobson.monitorerrorapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.angelorobson.monitorerrorapp.models.OpsErrorModel

class RecipesDiffUtil(
    private val oldList: List<OpsErrorModel>,
    private val newList: List<OpsErrorModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}