package ir.bokaeyan.factory.api

import androidx.lifecycle.MutableLiveData
import ir.bokaeyan.factory.supporthelper.ApplicationController
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback


/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {


    protected fun <T> getResult(call:()-> Call<T>):MutableLiveData<Result<T>> {

        var data= MutableLiveData<Result<T>>()
        val response = call()
        ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.LOADING,null,"در حال اتصال ...."))
        data.value=Result(Result.Status.LOADING,null,"")
        val callback: Callback<T> = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    if (response.isSuccessful) {
                        ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.SUCCESS,null,"با موفقیت دریافت شد"))
                        data.value=Result(Result.Status.SUCCESS,response.body(),"")
                    }
                    else
                    {
                        ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.ANOTHER,null,response.code().toString()))
                        data.value=Result(Result.Status.ANOTHER,null,"")
                    }
                } catch (ee: Exception) {
                    ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.ERROR,null,ee.message))
                    data.value=Result(Result.Status.ERROR,null,"")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.ERROR,null,"خطا"+t.message))
                data.value=Result(Result.Status.ANOTHER,null,"")
                t.printStackTrace()
            }
        }
        response.enqueue(callback)
        return data;
       /*try {
            var data= MutableLiveData<Result<T>>()
            ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.LOADING,null,"در حال اتصال ...."))
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.SUCCESS,null,"با موفقیت دریافت شد"))
                if (body != null) return Result.success(body)
            }
            ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.ANOTHER,null,"در حال اتصال ...."))
            return Result.another(response.code().toString())
           // return error(" ${response.code()} ${response.message()}")

        } catch (e: Exception) {
            ApplicationController.applicationContext().listener?.onStartCal(Result(Result.Status.ERROR,null,"اتصال به اینترنت را بررسی کنید"))

            return Result.error("اتصال به اینترنت را بررسی کنید")
        }*/
    }

    /*private fun <T> error(message: String): Result<T> {
        //Timber.e(message)

        return Result.error("اتصال به اینترنت را بررسی کنید")
    }*/

}

