package uz.gita.bankup.data.api.pref

import android.content.Context
import uz.gita.bankup.app.App
import uz.gita.bankup.data.api.enum.StartScreenEnum
import uz.gita.bankup.utils.startScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor() {

    companion object {
        private lateinit var instance: SharedPref
        fun getShared():SharedPref{
            if (!::instance.isInitialized){
                instance = SharedPref()
            }
            return instance
        }

        private const val START_SCREEN = "asdfghjkl"
    }

    private val pref = App.instance.getSharedPreferences("ContactApp", Context.MODE_PRIVATE)

    var refresh_token: String
        set(value) {
            pref.edit().putString("refresh_token", value).apply()
        }
        get() = pref.getString("refresh_token", "")!!

    var access_token: String
        set(value) {
            pref.edit().putString("access_token", value).apply()
        }
        get() = pref.getString("access_token", "")!!

    var phoneNumber: String
        set(value) {
            pref.edit().putString("phoneNumber", value).apply()
        }
        get() = pref.getString("phoneNumber", "+998901234567")!!

    var firstname: String
        set(value) {
            pref.edit().putString("firstname", value).apply()
        }
        get() = pref.getString("firstname", "")!!


    var lastName: String
        set(value) {
            pref.edit().putString("lastName", value).apply()
        }
        get() = pref.getString("lastName", "+998901234567")!!

    var password: String
        set(value) {
            pref.edit().putString("password", value).apply()
        }
        get() = pref.getString("password", "+998901234567")!!


    var startScreen: StartScreenEnum
        get() = pref.getString(START_SCREEN, StartScreenEnum.MAIN.name)!!.startScreen()
        set(startScreen) = pref.edit().putString(START_SCREEN, startScreen.name).apply()

}