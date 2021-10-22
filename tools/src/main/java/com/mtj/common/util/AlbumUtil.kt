package com.mtj.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.util.Base64
import com.mtj.common.base.BaseAppImpl
import java.io.*


class AlbumUtil {

    companion object {
        fun fileBase64String(path: String): String? {
            return try {
                val fis = FileInputStream(File(path))
                //val fis: InputStream = context.contentResolver.openInputStream(url) ?: return ""
                //转换成输入流
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var count = 0
                while (fis.read(buffer).also { count = it } >= 0) {
                    baos.write(buffer, 0, count) //读取输入流并写入输出字节流中
                }
                fis.close() //关闭文件输入流
                Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            } catch (e: Exception) {
                ""
            }
        }
//        fun insertFileIntoMediaStore(context: Context, fileName: String): Uri? {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                return null
//            }
//            val resolver: ContentResolver = context.contentResolver
//            //设置文件参数到ContentValues中
//            val values = ContentValues()
//            //设置文件名
//            values.put(MediaStore.Downloads.DISPLAY_NAME, fileName)
//            //设置文件描述，这里以文件名为例子
//            //values.put(MediaStore.Downloads.DESCRIPTION, fileName)
//            //设置文件类型
//            values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive")
//            //注意RELATIVE_PATH需要targetVersion=29
//            //故该方法只可在Android10的手机上执行
//            values.put(MediaStore.Downloads.RELATIVE_PATH, "/video")
//            //EXTERNAL_CONTENT_URI代表外部存储器
//            val external = MediaStore.Downloads.EXTERNAL_CONTENT_URI
//            //insertUri表示文件保存的uri路径
//            return resolver.insert(external, values)
//        }
//
//        fun fileBase64String(context: Context, fileName: String): String? {
//            return try {
//                val uri = insertFileIntoMediaStore(context, fileName) ?: Uri.parse(fileName)
//                val fis: InputStream = context.contentResolver.openInputStream(uri)!!
//                //转换成输入流
//                val baos = ByteArrayOutputStream()
//                val buffer = ByteArray(1024)
//                var count = 0
//                while (fis.read(buffer).also { count = it } >= 0) {
//                    baos.write(buffer, 0, count) //读取输入流并写入输出字节流中
//                }
//                fis.close() //关闭文件输入流
//                Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
//            } catch (e: Exception) {
//                null
//            }
//        }

        /**
         * base64字符串转视屏
         * videoFilePath  输出视频文件路径带文件名
         */
        fun base64ToVideo(base64: String): String? {
            try {
                //base解密
                val videoByte = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
                val videoFile = File(Environment.getExternalStorageDirectory().toString() + "/convert.mp4")
                if (videoFile.exists()) {
                    videoFile.delete()
                }
                try {
                    //创建文件
                    videoFile.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                //输入视频文件
                val fos = FileOutputStream(videoFile)
                fos.write(videoByte, 0, videoByte.size)
                fos.flush()
                fos.close()
                return videoFile.path
            } catch (e: IOException) {
                return null
            }
        }

        /**
         * 压缩到固定大小（100k）
         *
         * @param image
         * @return
         */
        fun imageToBase64(path: String?) {
            val bitmap = BitmapFactory.decodeFile(path)
            val baos = ByteArrayOutputStream()
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                baos
            ) // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            var options = 100
            while (baos.toByteArray().size / 1024 >= 100 && options > 0) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                val s = baos.toByteArray().size / 1024
                baos.reset() // 重置baos即清空baos
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    options,
                    baos
                ) // 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10 // 每次都减少10
            }
        }

        fun compress(srcPath: String?): String? {
            val sizeKB = 500 // 最大 600kB
            val hh = 720f
            val ww = 480f
            val opts = BitmapFactory.Options()
            opts.inJustDecodeBounds = true
            var bitmap = BitmapFactory.decodeFile(srcPath, opts)
            opts.inJustDecodeBounds = false
            val w = opts.outWidth
            val h = opts.outHeight
            var size = 0
            size = if (w <= ww && h <= hh) {
                1
            } else {
                val scale = if (w >= h) (w / ww).toDouble() else h / hh.toDouble()
                val log = Math.log(scale) / Math.log(2.0)
                val logCeil = Math.ceil(log)
                2
            }
            opts.inSampleSize = size
            bitmap = BitmapFactory.decodeFile(srcPath, opts)
            val baos = ByteArrayOutputStream()
            var quality = 100
            bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            println(baos.toByteArray().size)
            while (baos.toByteArray().size > sizeKB * 1024) {
                baos.reset()
                quality -= 20
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                println(baos.toByteArray().size)
            }
            try {
                val file = File(BaseAppImpl.context.cacheDir, System.currentTimeMillis().toString() + ".jpg")
                baos.writeTo(FileOutputStream(file))
                return file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    baos.flush()
                    baos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }


        /**
         * 给出url，获取视频的第一帧
         * @param url
         * @return
         */
        fun getVideoThumbnail(url: String?): Bitmap? {
            var bitmap: Bitmap? = null
            //MediaMetadataRetriever 是android中定义好的一个类，提供了统一的接口，用于从输入的媒体文件中取得帧和元数据；
            val retriever = MediaMetadataRetriever()
            try {
                //根据文件路径获取缩略图
                retriever.setDataSource(url, HashMap())
                //获得第一帧图片
                bitmap = retriever.frameAtTime
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } finally {
                retriever.release()
            }
            return bitmap
        }
    }

}