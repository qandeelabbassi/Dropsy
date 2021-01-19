package com.qandeelabbassi.dropsy

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Typeface
import android.util.Log
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

/**
 * Created by qandeel.rasheed on 8/12/2020 at 10:43 PM.
 */
object FontCache {
    private val fontCache = HashMap<String, Typeface?>()
    fun getTypeface(fontname: String, context: Context): Typeface? {
        var typeface = fontCache[fontname]
        if (typeface == null) {
            typeface = try {
                getFontFromRes(
                    context,
                    context.resources.getIdentifier(fontname, "raw", context.packageName)
                )
            } catch (e: Exception) {
                return null
            }
            fontCache[fontname] = typeface
        }
        return typeface
    }

    private fun getFontFromRes(context: Context, resource: Int): Typeface? {
        val tf: Typeface?
        var inputStream: InputStream? = null
        try {
            inputStream = context.resources.openRawResource(resource)
        } catch (e: NotFoundException) {
            Log.e("test", "Could not find font in resources!")
        }
        val outPath = context.cacheDir.toString() + "/tmp" + System.currentTimeMillis() + ".raw"
        try {
            val buffer = ByteArray(inputStream!!.available())
            val bos = BufferedOutputStream(FileOutputStream(outPath))
            var l: Int
            while (inputStream.read(buffer).also { l = it } > 0) bos.write(buffer, 0, l)
            bos.close()
            tf = Typeface.createFromFile(outPath)

            // clean up
            File(outPath).delete()
        } catch (e: Exception) {
            Log.e("Test", "Error reading in font!")
            return null
        }
        Log.d("test", "Successfully loaded font.")
        return tf
    }
}