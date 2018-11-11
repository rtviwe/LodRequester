package com.example.roman.lodaddplaction.createRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.database.converters.DormitoryTypeConverter
import com.example.roman.lodaddplaction.model.Dormitory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.step1_fragment.*

class FragmentCreateStep1 : Fragment() {

    private val dormitories = listOf(
        Dormitory.GORNYAK1.title, Dormitory.GORNYAK2.title, Dormitory.M1.title,
        Dormitory.M2.title, Dormitory.M3.title, Dormitory.M4.title, Dormitory.DSG.title
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.step1_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_dropdown_item, dormitories
        )

        spinner_dormitory.apply {
            adapter = spinnerAdapter
            setSelection(0)
        }
    }

    fun showError(view: TextInputEditText, messageRes: Int) {
        view.error = getString(messageRes)
    }

    fun getNameOfRequest() = et_name_of_request.text.toString()

    fun getDescOfRequest() = et_desc_of_request.text.toString()

    fun getDormOfRequest() = DormitoryTypeConverter().toDormitory(spinner_dormitory.selectedItemPosition)
}
