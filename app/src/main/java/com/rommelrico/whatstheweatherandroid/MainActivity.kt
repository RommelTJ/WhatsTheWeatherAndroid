package com.rommelrico.whatstheweatherandroid

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder



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
        try {
            val task = DownloadTask()
            val encodedCityName = URLEncoder.encode(editText?.text.toString(), "UTF-8")

            task.execute("http://api.openweathermap.org/data/2.5/weather?q=$encodedCityName&appid=a250a9d02ff1ad0ce1d34ea63cb6df33")

            val mgr = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(editText?.getWindowToken(), 0)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Could not find weather :(", Toast.LENGTH_SHORT).show()
        }
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
            val url: URL
            val urlConnection: HttpURLConnection?

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
            }

            return result
        }

        /**
         *
         * Runs on the UI thread after [.doInBackground]. The
         * specified result is the value returned by [.doInBackground].
         *
         *
         * This method won't be invoked if the task was cancelled.
         *
         * @param result The result of the operation computed by [.doInBackground].
         *
         * @see .onPreExecute
         *
         * @see .doInBackground
         *
         * @see .onCancelled
         */
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val jsonObject = JSONObject(result)
                val weatherInfo = jsonObject.getString("weather")
                Log.i("Weather content", weatherInfo)

                val arr = JSONArray(weatherInfo)
                var message = ""

                for (i in 0 until arr.length()) {
                    val jsonPart = arr.getJSONObject(i)

                    val main = jsonPart.getString("main")
                    val description = jsonPart.getString("description")

                    if (main != "" && description != "") {
                        message += main + ": " + description + "\r\n"
                    }
                }

                if (message != "") {
                    resultTextView?.text = message
                } else {
                    Toast.makeText(applicationContext, "Could not find weather :(", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Could not find weather :(", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } // end onPostExecute.

    } // end DownloadTask

}
