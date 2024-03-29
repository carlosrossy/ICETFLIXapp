package com.example.icetflixapp.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.icetflixapp.model.Category
import com.example.icetflixapp.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class CategoryTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor =  Executors.newSingleThreadExecutor()

    interface Callback{
        fun onPreExecute()
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
    }

    fun execute(url: String){
        callback.onPreExecute()

        executor.execute {
            var urlConnetction: HttpURLConnection? = null
            var buffer: BufferedInputStream? = null
            var stream: InputStream? = null

            try {
                val requestURL = URL(url)
                urlConnetction = requestURL.openConnection() as HttpURLConnection
                urlConnetction.readTimeout = 2000
                urlConnetction.connectTimeout = 2000

                val statusCode: Int = urlConnetction.responseCode
                if (statusCode > 400){
                    throw IOException("Erro na comunicação com o servidor!!")
                }

                stream =  urlConnetction.inputStream
               // val  jsonAsString = stream.bufferedReader().use { it.readText() }
               // Log.e("test", jsonAsString)

                buffer = BufferedInputStream(stream)
                val jsonAsString = toString(buffer)

                val categories = toCategories(jsonAsString)

                handler.post {
                    callback.onResult(categories)
                }

            } catch (e: Exception){
                val message = e.message ?: "erro desconhecido"
                Log.e("test", message , e)

                handler.post {
                    callback.onFailure(message)
                }

            }finally {
                urlConnetction?.disconnect()
                stream?.close()
                buffer?.close()
            }
        }
    }

    private fun toCategories(jsonAsString: String) : List<Category>{
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")
        for (i in 0  until jsonCategories.length()){
            val jsonCategory = jsonCategories.getJSONObject(i)

            val title = jsonCategory.getString("title")
            val jsonMovies = jsonCategory.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for(j in 0 until jsonMovies.length()){
                val jsonMovie = jsonMovies.getJSONObject(j)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(id, coverUrl))
            }

            categories.add(Category(title, movies))
        }
        return categories
    }

    private fun toString(stream: InputStream) : String {
            val bytes = ByteArray(1024)
            val baos =  ByteArrayOutputStream()
            var read: Int
            while(true){
                read = stream.read(bytes)
                if(read <= 0){
                    break
                }
                baos.write(bytes, 0, read)
            }
        return String(baos.toByteArray())
    }
}