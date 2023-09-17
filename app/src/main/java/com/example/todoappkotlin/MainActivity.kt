package com.example.todoappkotlin

import android.app.Dialog
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var categories = listOf(
        TaskCategory.Personal,
        TaskCategory.Business,
        TaskCategory.Others
    )

    private var tasks = mutableListOf(
        Task("prueba business", TaskCategory.Business),
        Task("prueba personal", TaskCategory.Personal),
        Task("prueba Other", TaskCategory.Others),
    )


    private lateinit var rvCategories : RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var rvTask: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    private lateinit var fabAddTask : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initUI()
        setListeners()
    }

    private fun setListeners() {
        fabAddTask.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val editTextTask: EditText = dialog.findViewById(R.id.editTextTask)
        val radioGroupCat: RadioGroup = dialog.findViewById(R.id.radioGroupCat)

        btnAddTask.setOnClickListener {
            val currentTask = editTextTask.text.toString()

            if (currentTask.isNotEmpty()) {

                val selectedId = radioGroupCat.checkedRadioButtonId
                val selectedRadioButton : RadioButton = radioGroupCat.findViewById(selectedId)
                val currentCategory : TaskCategory = when(selectedRadioButton.text) {
                    getString(R.string.business) -> TaskCategory.Business
                    getString(R.string.personal) -> TaskCategory.Personal
                    else -> TaskCategory.Others
                }

                tasks.add(Task(currentTask, currentCategory))
                updateTask()
                dialog.hide()

            }


        }

        dialog.show()
    }


    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTask = findViewById(R.id.rvTask)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) {position -> updateCategories(position)}
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks, ) { position -> onItemSelected(position)}
        rvTask.layoutManager = LinearLayoutManager(this)
        rvTask.adapter = tasksAdapter

    }

    private fun onItemSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected
        updateTask()
    }

    private fun updateCategories(position: Int) {
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTask()
    }

    private fun updateTask() {
        val selectedCategories: List<TaskCategory> = categories.filter{it.isSelected}
        val newTask = tasks.filter{selectedCategories.contains(it.category)}
        tasksAdapter.tasks = newTask
        tasksAdapter.notifyDataSetChanged()
    }
}