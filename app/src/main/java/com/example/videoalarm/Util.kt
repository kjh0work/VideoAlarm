package com.example.videoalarm

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

val daysList_en
= listOf(R.string.Sunday_en,R.string.Monday_en,R.string.Tuesday_en,R.string.Wednesday_en,R.string.Thursday_en,R.string.Friday_en,R.string.Saturday_en)

val daysList_en2
= listOf(R.string.Sunday_en2,R.string.Monday_en2,R.string.Tuesday_en2,R.string.Wednesday_en2,R.string.Thursday_en2,R.string.Friday_en2,R.string.Saturday_en2)

fun copyVideoToInternalStorage(context : Context, uri: Uri): Uri?{
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = uri.toString()
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        Uri.fromFile(file)
    }catch (e : Exception){
        Log.e("VideoCopy", "Error copying video to internal storage", e)
        null
    }
}

fun deleteVideoFile(uri: Uri) {
    val file = File(uri.path)
    if (file.exists()) {
        file.delete()
    }
}