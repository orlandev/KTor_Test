package com.ondev.ktor_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.ondev.ktor_test.data.remote.PostsService
import com.ondev.ktor_test.data.remote.dto.PostResponse
import com.ondev.ktor_test.ui.theme.KTor_TestTheme

class MainActivity : ComponentActivity() {

    private val service = PostsService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val posts = produceState<List<PostResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getPosts()
                }
            )

            KTor_TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(posts.value) { currentPost ->
                            Card(modifier = Modifier.fillMaxWidth()) {

                                Column(modifier = Modifier.fillMaxSize()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = currentPost.id.toString())
                                        Text(text = currentPost.title)
                                    }
                                    Text(text = currentPost.body)
                                }

                            }
                        }
                    }

                }
            }
        }
    }
}
