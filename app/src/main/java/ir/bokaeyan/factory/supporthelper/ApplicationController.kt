package ir.bokaeyan.factory.supporthelper

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import ir.bokaeyan.factory.api.APIServiceCallback


class ApplicationController  : MultiDexApplication()
{
    private var mAppExecutors: AppExecutors? = null
    var listener: APIServiceCallback? = null
    init {
       instance = this
   }
    companion object {
        private var instance: ApplicationController? = null

        fun applicationContext() : ApplicationController {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Benyamin.TTF")
        mAppExecutors = AppExecutors()


    }



    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}