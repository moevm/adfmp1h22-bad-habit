package com.example.badhabits

import junit.framework.TestCase
import org.json.JSONArray
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.json.JSONObject
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.mockito.*

class FileControllerTest : TestCase() {
    val filename = "userHabbitsTest"
    var testFata = "this is a testing data!"

    @Test
    fun testSaveToFile() {
        val file_controller: FileController = FileController()
        file_controller.saveToFile(testFata, FileOutputStream(filename))
        assertEquals(File(filename).exists(), true)
    }

    @Test
    fun testLoadFromFile() {
        var str: String? = ""
        val obj = JSONObject(str.toString())
        val obj2 = JSONArray(testFata)
        var userDateFromFile: JSONArray = JSONArray()
        val mockBookService = Mockito.mock(JSONArray::class.java)
        val file_Controller: FileController = FileController()
        userDateFromFile  = file_Controller.loadFromFile(FileInputStream(filename))
        assertEquals("this is a testing data!", testFata)
    }
}