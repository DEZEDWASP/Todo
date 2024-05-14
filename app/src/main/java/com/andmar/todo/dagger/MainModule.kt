package com.andmar.todo

import android.content.Context
import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.andmar.todo.data.TodoDao
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.data.OfflineTodoRepository
import com.andmar.todo.data.AppContainer
import com.andmar.todo.data.AppTodoContainer
import com.andmar.todo.CountNotificationService

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    
    /*
    @Provides
    @Singleton
    fun provideNotificationService(context: Context): CountNotificationService {
        return CountNotificationService(context)
    }
    */
    
    @Provides
    @Singleton
    fun getTodoRepository(context: Context): TodoRepository {
        return AppTodoContainer(context).todoRepository
    }
    
    @Provides
    fun getContext(application: Application): Context {
        return application.applicationContext
    }
    
}