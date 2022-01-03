package com.example.pokeapp.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokeapp.ui.theme.PokeAppTheme

class SearchActivity : ComponentActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(viewModel.state.observeAsState().value?.greeting ?: "Not Found")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokeAppTheme {
        Greeting("Android")
    }
}