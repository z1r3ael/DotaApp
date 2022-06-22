package com.example.week8dotafragmentsappwb

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.example.week8dotafragmentsappwb.data.Utils
import com.example.week8dotafragmentsappwb.model.CharacterItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import java.io.*
import java.lang.StringBuilder
import java.lang.reflect.ParameterizedType

class SplashScreenActivity : AppCompatActivity() {

    private var characterItems: ArrayList<CharacterItem> = ArrayList()
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedJson = readFromFile()

        if (savedJson != null) {
            Toast.makeText(this, "Данные загружены из файла", Toast.LENGTH_LONG).show()
            createDotaCharactersList(savedJson)
        } else {
            showMeetingAlertDialog()
        }
    }

    private fun showMeetingAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Загрузка данных")
            .setMessage("На устройстве отсутсвуют сохраненные данные. Загрузить из интернета?")
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                requestCharacters()
            }.show()
    }

    private fun requestCharacters() {
        if (isOnline(this)) {
            val request = Request.Builder().url(Utils.URL).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val receivedJson = response.body!!.string()
                        writeToFile(receivedJson)
                        createDotaCharactersList(receivedJson)
                    }
                }
            })
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Отсутствует соединение с сетью")
                .setMessage("Проверьте ваще подключение к сети и перезапустите приложение")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                    finish()
                }.show()
        }
    }

    private fun createDotaCharactersList(receivedJson: String) {
        val myList: ParameterizedType = Types.newParameterizedType(
            List::class.java, CharacterItem::class.java
        )
        val jsonAdapter: JsonAdapter<ArrayList<CharacterItem>> =
            moshi.adapter(myList)
        characterItems =
            jsonAdapter.fromJson(receivedJson)!!

        // Каждому элементу списка корректируется ссылка на изображение
        for (i in characterItems.indices) {
            val currentVal: String = characterItems[i].characterIcon
            characterItems[i].setCharacterIcon(Utils.BASE_URL, currentVal)
        }

        val charactersListBundle = Bundle().apply {
            putParcelableArrayList(Utils.CHARACTERS_LIST_KEY, characterItems)
        }
        val intent = Intent(
            this,
            MainActivity::class.java
        )
        intent.putExtras(charactersListBundle)
        startActivity(intent)
        finish()
    }

    private fun writeToFile(savedJson: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = openFileOutput(Utils.FILE_NAME, MODE_PRIVATE)
            fileOutputStream.write(savedJson.toByteArray())
            runOnUiThread {
                Toast.makeText(this, "Данные сохранены - $filesDir/${Utils.FILE_NAME}", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun readFromFile(): String? {
        var fileInputStream: FileInputStream? = null
        var savedJson: String? = null
        try {
            fileInputStream = openFileInput(Utils.FILE_NAME)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            val text: String = bufferedReader.readText()
            stringBuilder.append(text)
            savedJson = stringBuilder.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return savedJson
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }
}