package com.example.icetflixapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class DownloadImageTask(private  val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor =  Executors.newSingleThreadExecutor()

    interface Callback{
        fun onResult(bitmap: Bitmap)
    }

    fun execute(url: String){
        executor.execute {

            var urlConnetction: HttpURLConnection? = null
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
                val bitmap = BitmapFactory.decodeStream(stream)

                handler.post {
                    callback.onResult(bitmap)
                }

            }catch (e: IOException){
                val message = e.message ?: "erro desconhecido"
                Log.e("test", message , e)
            }finally {
                urlConnetction?.disconnect()
                stream?.close()
            }
        }
    }
}