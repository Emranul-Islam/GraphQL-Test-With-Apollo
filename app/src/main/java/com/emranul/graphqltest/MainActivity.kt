package com.emranul.graphqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.emranul.graphqltest.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.File

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val file = File(applicationContext.cacheDir, "firstCache")
        val size:Long = 1024*1024
        val cacheStore  = DiskLruHttpCacheStore(file,size)



        val clint = ApolloClient.builder()
            .serverUrl("http://103.69.150.122:8003/graphql")
            .httpCache(ApolloHttpCache(cacheStore))
            .build()


        clint.query(MyFirstQueryTryQuery())
            .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
            .enqueue(object : ApolloCall.Callback<MyFirstQueryTryQuery.Data>() {
                override fun onResponse(response: Response<MyFirstQueryTryQuery.Data>) {
                    Log.d(TAG, "onResponse: ${response.data}")

                    response.data?.posts?.data?.let {

                        runOnUiThread {

                            val myAdapter = MyAdapter(it)

                            binding.recycler.apply {
                                layoutManager = LinearLayoutManager(this@MainActivity)
                                adapter = myAdapter
                            }
                        }
                    }

                }

                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "onFailure: ${e.message}")
                }
            })


    }

    companion object {
        private const val TAG = "MainActivity"
    }
}