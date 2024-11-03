package com.example.videoalarm

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

val daysList_en
= listOf(R.string.Sunday_en,R.string.Monday_en,R.string.Tuesday_en,R.string.Wednesday_en,R.string.Thursday_en,R.string.Friday_en,R.string.Saturday_en)

val daysList_en2
= listOf(R.string.Sunday_en2,R.string.Monday_en2,R.string.Tuesday_en2,R.string.Wednesday_en2,R.string.Thursday_en2,R.string.Friday_en2,R.string.Saturday_en2)

fun getFileFromExternalStorage(context: Context, filename : String): File {

    return File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), filename)
}

/**
 * 외부 저장소에 접근이 가능한지 확인
 */
fun isExternalStorageWritable():Boolean{
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

fun saveFileToExternalStorage(context: Context, videoUri : Uri) : String{
    val fileName = getFileName(context, videoUri)
    val file = getFileFromExternalStorage(context, fileName)
    Log.d("ExternalStorage", "saveFileToExternalStorage메서드 들어옴.")
    // 파일이 이미 존재하는지 확인
    if (file.exists()) {
        Log.d("ExternalStorage", "파일이 이미 존재합니다: ${file.absolutePath}")
        return fileName
    }

    try {
        val inputStream = context.contentResolver.openInputStream(videoUri)
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input?.copyTo(output)
            }
        }
    }catch (e : Exception){
        Log.e("ExternalStorage","can't openInputStream",e)
    }
    Log.d("ExternalStorage", "file save clear")
    return fileName
}

fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = cursor.getString(index)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "${System.currentTimeMillis()}_video.mp4"
}
