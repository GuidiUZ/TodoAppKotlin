package com.example.todoappkotlin

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TasksViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val tvTask : TextView = view.findViewById(R.id.tvTask)
    private val checkboxTask : CheckBox = view.findViewById(R.id.checkboxTask)

    fun render(task: Task) {
        tvTask.text = task.name

        if (task.isSelected) {
            tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        checkboxTask.isChecked = task.isSelected

        val color = when (task.category) {
            TaskCategory.Business -> R.color.todo_business_category
            TaskCategory.Others -> R.color.todo_other_category
            TaskCategory.Personal -> R.color.todo_personal_category
        }

        checkboxTask.buttonTintList = ColorStateList.valueOf(
            ContextCompat.getColor(checkboxTask.context, color)
        )


    }
}