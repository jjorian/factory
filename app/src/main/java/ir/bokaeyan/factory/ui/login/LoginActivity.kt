package ir.bokaeyan.factory.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.bokaeyan.factory.R
import ir.bokaeyan.factory.respository.UserRespository
import ir.bokaeyan.factory.api.Result
import ir.bokaeyan.factory.domain.LoginClass
import ir.bokaeyan.factory.supporthelper.AppPreferences
import ir.bokaeyan.factory.supporthelper.toast
import java.lang.reflect.Type

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(AppPreferences.ID>0)
        {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        login.setOnClickListener {

            UserRespository.getInstance().signIn(username.text.toString(),password.text.toString()).observe(this, Observer { result->

                when(result.status) {
                    Result.Status.SUCCESS ->
                    {
                        val isExist=result.data!!.isExist
                        if(isExist==1) {
                           val objecttype: Type =object: TypeToken<List<LoginClass>>(){}.type
                            var ff=Gson().fromJson<List<LoginClass>>(result.data!!.list, objecttype)

                            AppPreferences.ID=ff[0]!!.CorpId
                            AppPreferences.UserName=ff[0]!!.FullName

                            
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)
                           finish()
                        }
                        else
                        {
                            toast("نام کاربری یا کلمه عبور اشتباه است",Toast.LENGTH_SHORT)
                        }
                        loading.visibility=View.GONE
                        //val tuser: TUser = result.data!!
                        //AppPreferences.ID = tuser.ID
                        // database?.tUserDao()?.delete(tuser.ID)
                        //database?.tUserDao()?.insert(tuser)

                    }
                    Result.Status.LOADING -> {
                        loading.visibility=View.VISIBLE

                    }
                    else -> {

                        loading.visibility=View.GONE
                    }
                }
            })
        }

    }


}
