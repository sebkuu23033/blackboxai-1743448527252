package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screens.TaskEditScreen
import com.example.todoapp.ui.screens.TaskListScreen
import com.example.todoapp.viewmodels.TaskViewModel

@Composable
fun TodoNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            TaskListScreen(
                viewModel = viewModel,
                onAddTask = { navController.navigate(Screen.TaskEdit.createRoute()) },
                onEditTask = { taskId ->
                    navController.navigate(Screen.TaskEdit.createRoute(taskId))
                }
            )
        }
        composable(
            route = Screen.TaskEdit.routeWithArgs,
            arguments = Screen.TaskEdit.arguments
        ) { navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getInt(Screen.TaskEdit.taskIdArg)
            TaskEditScreen(
                taskId = taskId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object TaskList : Screen("taskList")
    object TaskEdit : Screen("taskEdit") {
        const val taskIdArg = "taskId"
        val routeWithArgs = "$route/{$taskIdArg}"
        val arguments = listOf(
            navArgument(taskIdArg) { nullable = true }
        )

        fun createRoute(taskId: Int? = null) = 
            if (taskId != null) "$route/$taskId" else route
    }
}