package com.example.roman.lodaddplaction.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.main.MainActivity
import com.example.roman.lodaddplaction.network.LoginNetworkInteractor
import com.example.roman.lodaddplaction.network.LoginRequestDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Activity() {

    private lateinit var loginInteractor: LoginNetworkInteractor
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkIfLogged()) {
            openMainActivity()
        } else {
            setContentView(R.layout.activity_login)
            loginInteractor = LoginNetworkInteractor()
            initButtons()
        }
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    private fun checkIfLogged(): Boolean {
        val token = getSharedPreferences("default", Context.MODE_PRIVATE)
                .getString("accessToken", "")
        return token?.length == 25
    }

    private fun initButtons() {
        b_login.setOnClickListener {
            if (checkInput()) {
                login()
            }
        }
    }

    private fun login() {
        enableInput(false)
        disposable = loginInteractor.login(LoginRequestDto(et_login.text.toString(),
                et_password.text.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getSharedPreferences("default", Context.MODE_PRIVATE)
                            .edit()
                            .putString("accessToken", it.accessToken)
                            .apply()

                    openMainActivity()
                    finish()
                }, {
                    it.printStackTrace()
                    Toast.makeText(baseContext, "Wrong creds", Toast.LENGTH_SHORT)
                            .show()
                    enableInput(true)
                })
    }

    private fun checkInput(): Boolean {
        var success = true

        if (et_login.text.toString().isEmpty()) {
            et_login.error = "Login should not be empty"
            success = false
        }

        if (et_password.text.toString().isEmpty()) {
            et_password.error = "Password should not be empty"
            success = false
        }

        return success
    }

    private fun enableInput(enable: Boolean) {
        et_login.isEnabled = enable
        et_password.isEnabled = enable
        b_login.isEnabled = enable
    }

    private fun openMainActivity() {
        Intent(this@LoginActivity, MainActivity::class.java).apply {
            this@LoginActivity.startActivity(this)
        }
    }
}