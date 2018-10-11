package com.example.roman.lodaddplaction.createRequest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.roman.lodaddplaction.R
import kotlinx.android.synthetic.main.step1_fragment.*

class CreateRequestActivity : AppCompatActivity() {

    private lateinit var titleStep1: String
    private lateinit var titleStep2: String

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_request)

        titleStep1 = getString(R.string.step_1_title)
        titleStep2 = getString(R.string.step_2_title)

        currentStep = 1

        setCurrentStepFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    override fun onBackPressed() {
        when (currentStep) {
            2 -> {
                currentStep = 1
                setCurrentStepFragment()
            }
            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_next -> {
                handleNextButton()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleNextButton() {
        when (currentStep) {
            1 -> {
                if (check()) {
                    currentStep = 2
                    setCurrentStepFragment()
                } else {
                    showErrors()
                }
            }
            2 -> {
                createRequest()
            }
        }
    }

    private fun check(): Boolean {
        if (!et_name_of_request.text!!.isBlank()
                and !et_desc_of_request.text!!.isBlank()) {
            return true
        }
        return false
    }

    private fun showErrors() {
        if (et_name_of_request.text!!.isEmpty()) {
            et_name_of_request.error = getString(R.string.error_no_name)
        }
        if (et_desc_of_request.text!!.isEmpty()) {
            et_desc_of_request.error = getString(R.string.error_no_description)
        }
    }

    private fun setCurrentStepFragment() {
        val fragment = when (currentStep) {
            1 -> {
                supportActionBar?.title = titleStep1
                FragmentCreateStep1()
            }
            2 -> {
                supportActionBar?.title = titleStep2
                FragmentCreateStep2()
            }
            else -> throw Exception("Step $currentStep not found")
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
    }

    private fun createRequest() {
        finish()
    }
}
