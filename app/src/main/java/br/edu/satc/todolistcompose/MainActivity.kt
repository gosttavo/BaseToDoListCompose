package br.edu.satc.todolistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import br.edu.satc.todolistcompose.TaskDao
import br.edu.satc.todolistcompose.ui.screens.HomeScreen
import br.edu.satc.todolistcompose.ui.theme.ToDoListComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "br.edu.satc.contatosapp"
        )
            .allowMainThreadQueries()
            .build()

        val taskDao = db.taskDao()

        setContent {
            ToDoListComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var tasksState by remember {
                        mutableStateOf(taskDao.getAll())
                    }

                    HomeScreen(tasks = tasksState, onNewTaskCreated = {
                        taskDao.insertAll(it)
                        tasksState = taskDao.getAll()
                    }) { task, complete ->
                        run {
                            task.complete = complete
                            taskDao.updateAll(task)
                        }
                    }
                }
            }
        }
        /*
        setContent {
            ContatosAppComposeRoomTheme {
                ContactApp(users) {
                    userDao.insertAll(it)
                }
            }
        }*/
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListComposeTheme {
        Greeting("Android")
    }
}