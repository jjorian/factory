package ir.bokaeyan.factory.ui.login

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ir.bokaeyan.factory.R
import ir.bokaeyan.factory.api.Result
import ir.bokaeyan.factory.respository.FactoryRespository
import ir.bokaeyan.factory.supporthelper.AppPreferences
import ir.bokaeyan.factory.supporthelper.toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val ACTION_SCAN = "com.google.zxing.client.android.SCAN"
    var ResultCodeCameraBarcoder = 2
    lateinit var txt_barcode: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_scan = findViewById<Button>(R.id.btn_scan)
        val btn_exit = findViewById<Button>(R.id.btn_exit)
        val btn_logout = findViewById<Button>(R.id.btn_logout)

        btn_exit.setOnClickListener {
            finish()
        }
        btn_logout.setOnClickListener {
            AppPreferences.ID=0;
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        txt_barcode = findViewById<TextView>(R.id.txt_fullname)
        txt_barcode.text=AppPreferences.UserName
        btn_scan.setOnClickListener {
            scanBar()
        }

    }

    fun scanBar() {
        try {
            val intent =
                Intent(ACTION_SCAN)
            //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(
                intent,
                ResultCodeCameraBarcoder
            )
        } catch (anfe: ActivityNotFoundException) {
            toast("برنامه بارکدخوان گوگل پیدا نشد\nBarcode Scanner")

        }
    }
    //کارخانه سیمان نیشابور - دستگاه : دستگاه سیمان 1 - کد دستگاه :1
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ResultCodeCameraBarcoder) {
            if (resultCode == Activity.RESULT_OK) {
                try {


                    val contents = data!!.getStringExtra("SCAN_RESULT")
                    val format = data!!.getStringExtra("SCAN_RESULT_FORMAT")
                    var barcodeSplited = contents.split("-")
                    var CropName = barcodeSplited[0].trim()

                    var barcodeSplitedDeviceName = barcodeSplited[1].split(":")
                    var DName = barcodeSplitedDeviceName[1].trim()

                    var barcodeSplitedDeviceID = barcodeSplited[2].split(":")
                    var DID = barcodeSplitedDeviceID[1].trim().toInt()

                    val intent = Intent(baseContext, UrlActivity::class.java)
                    val url =
                        AppPreferences.BaseUrl + "Mobile/AddData?UID=" + AppPreferences.ID.toString() + "&DID=" + DID.toString() + "&UName=" + AppPreferences.UserName + "&DName=" + DName + "&CorpName=" + CropName
                    // txt_barcode.text=url

                    intent.putExtra("url", url)
                    startActivity(intent)
                    //toast(id.toString())
                    /*txt_barcode.text = id.toString()
                val intent = Intent(baseContext, UrlActivity::class.java)
                FactoryRespository.getInstance().AddData(AppPreferences.ID,id).observe(this, Observer { result->

                    when(result.status) {
                        Result.Status.SUCCESS ->
                        {
                            val isExist=result.data!!.isExist
                            if(isExist==1) {

                                intent.putExtra("url",AppPreferences.BaseUrl+"mobile/AddData/"+AppPreferences.ID+"/"+id)
                                startActivity(intent)
                            }
                            else
                            {
                                toast("پیدا نشد", Toast.LENGTH_SHORT)
                            }
                            //loading.visibility= View.GONE
                            //val tuser: TUser = result.data!!
                            //AppPreferences.ID = tuser.ID
                            // database?.tUserDao()?.delete(tuser.ID)
                            //database?.tUserDao()?.insert(tuser)

                        }
                        Result.Status.LOADING -> {
                           // loading.visibility= View.VISIBLE

                        }
                        else -> {

                            //loading.visibility= View.GONE
                        }
                    }
                })*/
                }catch (ex:Exception)
                {
                    toast("خطایی در حین ایجاد لینک رخ داده است")
                }


            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
            }
        }
    }
}