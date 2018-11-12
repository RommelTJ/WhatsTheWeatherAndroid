package com.rommelrico.whatstheweatherandroid

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var editText: EditText? = null
    var resultTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.resultTextView)
    }

    fun getWeather(view: View) {
        // TODO: Implement me.
    }

    inner class DownloadTask: AsyncTask<String,Void,String>() {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to [.execute]
         * by the caller of this task.
         *
         * This method can call [.publishProgress] to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         *
         * @return A result, defined by the subclass of this task.
         *
         * @see .onPreExecute
         * @see .onPostExecute
         *
         * @see .publishProgress
         */
        override fun doInBackground(vararg params: String?): String {
            var result = ""
            var url: URL
            var urlConnection: HttpURLConnection? = null

            try {

                url = URL(params[0])
                urlConnection = url.openConnection() as HttpURLConnection
                val `in` = urlConnection.inputStream
                val reader = InputStreamReader(`in`)
                var data = reader.read()

                while (data != -1) {
                    val current = data.toChar()
                    result += current
                    data = reader.read()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Could not find weather :(", Toast.LENGTH_SHORT).show()
            }

            return result
        }

    }

}
