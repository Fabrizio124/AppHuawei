package com.sandoval.hselfiecamera

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btnLogin.setOnClickListener {
            loginHuaweiIdAuth()
        }

    }

    private fun loginHuaweiIdAuth() {
        val mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setEmail()
            .setAccessToken()
            .setProfile()
            .setUid()
            .setId()
            .createParams()
        val mAuthManager = HuaweiIdAuthManager.getService(this, mAuthParams)
        startActivityForResult(
            mAuthManager.signInIntent,
            1000
        ) // este es el numero de identificacion de servicio de login
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Login Cancelado", Toast.LENGTH_LONG)
            } else if (resultCode == Activity.RESULT_OK){
                val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if(authHuaweiIdTask.isSuccessful){
                    Toast.makeText(this, "Login Exitoso!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Login Fallo!", Toast.LENGTH_LONG)
                }
            }

        }
    }
}
