package com.arildojr.shufflesongs

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

open class BaseTest {

    protected inline fun <reified T> createTestData(jsonStr: String?): T {
        val listType = object : TypeToken<T>(){}.type
        return GsonBuilder().create().fromJson<T>(jsonStr, listType)
    }

    protected fun loadJsonFromAsset(fileName: String): String? {
        val jsonStr: String?
        var inputStream: InputStream? = null

        try {
            inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
            val size = inputStream?.available() ?: 0
            val buffer = ByteArray(size)
            inputStream?.read(buffer)
            jsonStr = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close()
        }
        return jsonStr
    }
}
