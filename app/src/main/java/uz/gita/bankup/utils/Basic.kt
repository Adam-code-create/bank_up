package uz.gita.bankup.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import uz.gita.bankup.data.api.enum.StartScreenEnum
import java.io.File

fun String.startScreen(): StartScreenEnum {
    return if(this == StartScreenEnum.AUTH.name) StartScreenEnum.AUTH
    else StartScreenEnum.MAIN
}

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}

fun timber(message: String, tag: String = "TTT"){
    Timber.tag(tag).d(message)
}

fun myLog(message: String, tag: String = "TTT") {
    Log.d(tag, message)
}

fun Fragment.showToast(message: String){
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun File.toRequestData() : MultipartBody.Part{
    val requestFile = this.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("avatar",name,requestFile)
}