package com.assem.onedriveintegration.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import com.assem.onedriveintegration.R
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { view -> inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0) }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun EditText.addTextWatcher(
    afterTextChanged: ((text: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s)
        }
    })
}

fun isValidPassword(password: String): Boolean {
    val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{10,}$".toRegex()
    return regex.matches(password)
}

fun isValidEmailFormat(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidSaudiPhoneNumber(phone: String): Boolean {
    val saudiPhoneRegex = "^((\\+9665|05)[0-9]{8})$".toRegex()
    return saudiPhoneRegex.matches(phone)
}

fun Uri.uriToBitmap(context: Context): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Bitmap.compressBitmap(quality: Int): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, stream) // Adjust quality (0-100)
    return stream.toByteArray()
}

fun Uri.saveCompressedImage(context: Context, quality: Int): File? {
    val bitmap = this.uriToBitmap(context) ?: return null
    val compressedBytes = bitmap.compressBitmap(quality)

    val compressedFile = File(context.cacheDir, "compressed_image.jpg")
    FileOutputStream(compressedFile).use { it.write(compressedBytes) }

    return compressedFile
}


fun ImageView.getFileIcon(extension: String) {
    val fileIcon = when (extension.lowercase()) {
        "pdf" -> R.drawable.ic_pdf_file
        "doc", "docx" -> R.drawable.ic_doc_file
        "txt" -> R.drawable.ic_txt_icon
        "ppt", "pptx" -> R.drawable.ic_ppt_file
        "xlsx", "xls" -> R.drawable.ic_excel_icon
        "zip", "rar" -> R.drawable.ic_zip_file
        "jpg", "png", "jpeg" -> R.drawable.ic_img_icon
        "mp4" -> R.drawable.ic_mp4
        else -> R.drawable.ic_corrupted_file_icon
    }
    loadImg(fileIcon, R.drawable.ic_file_icon)
}


fun ImageView.loadImg(
    drawableId: Int,
    placeHolder: Int?
) {
    val glideImg = Glide.with(context).load(drawableId)
    placeHolder?.run { glideImg.placeholder(placeHolder).error(placeHolder).fallback(placeHolder) }
    glideImg.into(this)
}

@SuppressLint("DefaultLocale")
fun Long.formatFileSize(): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = this.toDouble()
    var index = 0

    while (size >= 1024 && index < units.size - 1) {
        size /= 1024
        index++
    }

    return String.format("%.2f %s", size, units[index])
}