package com.example.todoapp.data.repository

import com.example.todoapp.data.dao.TaskDao
import com.example.todoapp.data.models.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    fun searchTasks(query: String): Flow<List<Task>> = taskDao.searchTasks(query)

    fun getTasksByCompletion(isCompleted: Boolean): Flow<List<Task>> = 
        taskDao.getTasksByCompletion(isCompleted)
}