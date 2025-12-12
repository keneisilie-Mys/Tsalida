package com.example.tsalida.destination

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object CommonFunction {
    fun shareImage(context: Context, pageNo: Int){
        try {
            val inputStream = context.assets.open("Hymns/p$pageNo.jpg")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val newFile = File(cachePath, "share_image.jpg")
            val stream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
            val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile)

            //Sharing two pages
            if (pageNo == 97 || pageNo == 167 || pageNo == 226 || pageNo == 242 || pageNo == 248 || pageNo == 254 || pageNo == 267 || pageNo == 274 || pageNo == 331 || pageNo == 427){
                shareTwoImage(context, pageNo+1, contentUri, cachePath)
                return
            }
            if (pageNo == 98 || pageNo == 168 || pageNo == 227 || pageNo == 243 || pageNo == 249 || pageNo == 255 || pageNo == 268 || pageNo == 275 || pageNo == 332 || pageNo == 428){
                shareTwoImage(context, pageNo-1, contentUri, cachePath)
                return
            }
            //Share only one page
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(contentUri, context.contentResolver.getType(contentUri))
                putExtra(Intent.EXTRA_STREAM, contentUri)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via..."))
        }catch (e: Exception){
            Log.d("ErrorTsalida", "ErrorTsalida")
        }
    }

    fun shareTwoImage(context: Context, pageNo: Int, contentUri: Uri, cachePath: File){
        try {
            val inputStream = context.assets.open("Hymns/p$pageNo.jpg")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            val newFile2 = File(cachePath, "share_image2.png")
            val stream = FileOutputStream(newFile2)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
            val contentUri2 = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile2)
            val arrayUri = ArrayList<Uri>()
            arrayUri.add(contentUri)
            arrayUri.add(contentUri2)
            val shareIntent2 = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayUri)
            }
            context.startActivity(Intent.createChooser(shareIntent2, "Share via..."))
        }catch (e: Exception){
            Log.d("Error in special Intent", "Error in special Intent")
        }
    }

    fun fixSongNo(songNo: Int): Int{
        var pageNo = songNo
        if (songNo>97 && songNo<=166) pageNo += 1
        if (songNo>166 && songNo<=224) pageNo += 2
        if (songNo>224 && songNo<=239) pageNo += 3
        if (songNo>239 && songNo<=244) pageNo += 4
        if (songNo>244 && songNo<=249) pageNo += 5
        if (songNo>249 && songNo<=261) pageNo += 6
        if (songNo>261 && songNo<=267) pageNo += 7
        if (songNo>267 && songNo<=323) pageNo += 8
        if (songNo>323 && songNo<=418) pageNo += 9
        if (songNo>418 && songNo<=423) pageNo += 10

        return pageNo
    }

    fun shareMoreImage(context: Context, pageNo: Int, route: String){
        val inputSteam = if(route == "readinglist" || route == "reading/{pageNo}") context.assets.open("Responsive Reading/prelude$pageNo.jpg") else context.assets.open("End Pages/end$pageNo.jpg")
        val bitmap = BitmapFactory.decodeStream(inputSteam)

        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val newFile = File(cachePath, "share_image.jpg")
        val outputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(contentUri, context.contentResolver.getType(contentUri))
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, contentUri)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share Via..."))
    }

}