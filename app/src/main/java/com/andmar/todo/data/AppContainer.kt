package com.andmar.todo.data

import android.content.Context

interface AppContainer {
    val todoRepository: TodoRepository
}

class AppTodoContainer(private val context: Context): AppContainer {

    override val todoRepository: TodoRepository by lazy {
        OfflineTodoRepository(TodoDatabase.getDatabase(context).todoDao())
    }
}
