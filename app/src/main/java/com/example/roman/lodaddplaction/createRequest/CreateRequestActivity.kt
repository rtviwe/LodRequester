package com.example.roman.lodaddplaction.createRequest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.data.getLocation
import com.example.roman.lodaddplaction.model.Dormitory
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag
import com.example.roman.lodaddplaction.model.User
import kotlinx.android.synthetic.main.step1_fragment.*

class CreateRequestActivity : AppCompatActivity() {

    private lateinit var titleStep1: String
    private lateinit var titleStep2: String
    private lateinit var nameOfRequest: String
    private lateinit var tagsOfRequest: List<Tag>
    private lateinit var descOfRequest: String
    private lateinit var dormOfRequest: Dormitory

    private lateinit var createRequestViewModel: CreateRequestViewModel
    private lateinit var firstStepFragment: FragmentCreateStep1
    private lateinit var secondStepFragment: FragmentCreateStep2
    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_request)

        titleStep1 = getString(R.string.step_1_title)
        titleStep2 = getString(R.string.step_2_title)
        createRequestViewModel = ViewModelProviders.of(this).get(CreateRequestViewModel::class.java)

        currentStep = 1

        firstStepFragment = FragmentCreateStep1()
        secondStepFragment = FragmentCreateStep2()
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
                collectInfoFromFirstFragment()
                if (check(nameOfRequest, descOfRequest)) {
                    currentStep = 2
                    setCurrentStepFragment()
                } else {
                    showErrorsOnFirstFragment()
                }
            }
            2 -> {
                collectInfoFromSecondFragment()
                createRequest()
            }
        }
    }

    private fun collectInfoFromFirstFragment() {
        nameOfRequest = firstStepFragment.getNameOfRequest()
        descOfRequest = firstStepFragment.getDescOfRequest()
        dormOfRequest = firstStepFragment.getDormOfRequest()
    }

    private fun collectInfoFromSecondFragment() {
        tagsOfRequest = secondStepFragment.getTagsOfRequest()
    }

    private fun check(first: String, second: String) = (!first.isBlank() and !second.isBlank())

    private fun showErrorsOnFirstFragment() {
        if (nameOfRequest.isBlank()) {
            firstStepFragment.showError(et_name_of_request, R.string.error_no_name)
        }
        if (descOfRequest.isBlank()) {
            firstStepFragment.showError(et_desc_of_request, R.string.error_no_description)
        }
    }

    private fun setCurrentStepFragment() {
        val fragment = when (currentStep) {
            1 -> {
                supportActionBar?.title = titleStep1
                firstStepFragment
            }
            2 -> {
                supportActionBar?.title = titleStep2
                secondStepFragment
            }
            else -> throw Exception("Step $currentStep not found")
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }

    private fun createRequest() {
        val location = getLocation(baseContext)

        val user = User(
            name = "Bob",
            password = "qwerty12345",
            avatarUrl = "",
            latitude = location?.latitude,
            longitude = location?.longitude
        )

        val request = Request(
            title = nameOfRequest,
            description = descOfRequest,
            dormitory = dormOfRequest,
            user = user
        )

        createRequestViewModel.addRequestAndTags(request, tagsOfRequest)
        finish()
    }
}
