package com.example.todoappkotlin

sealed class TaskCategory (var isSelected:Boolean = true){

    object Personal : TaskCategory()
    object Business : TaskCategory()
    object Others : TaskCategory()
}