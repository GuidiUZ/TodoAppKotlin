package com.example.todoappkotlin

data class Task (val name:String, val category: TaskCategory, var isSelected:Boolean = false ) {

}