package com.example.badhabits



import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

import java.util.*

class fileController {
    var filename = "userHabbits"

    fun loadFromFile(inputStream: FileInputStream   ): JSONArray {
        var userDateFromFile: JSONArray = JSONArray()

        try {

            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(
                    inputStream
                )
            )
            var str: String? = ""
            // читаем содержимое
            while (br.readLine().also { str = it } != null) {
                val obj = JSONObject(str.toString())
                userDateFromFile.put(obj)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return  userDateFromFile
    }
    fun saveToFile(tmpUserData:String, outputStream: FileOutputStream)
    {
        try {
            // отрываем поток для записи
            val bw = BufferedWriter(
                OutputStreamWriter(outputStream)
            )
            // пишем данные
            bw.write(tmpUserData)
            // закрываем поток
            bw.close()
            Log.d("fileIn", "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}