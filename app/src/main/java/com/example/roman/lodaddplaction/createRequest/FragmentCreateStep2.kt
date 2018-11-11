package com.example.roman.lodaddplaction.createRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.model.Tag
import com.example.roman.lodaddplaction.model.TagType
import kotlinx.android.synthetic.main.step2_fragment.*

class FragmentCreateStep2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.step2_fragment, container, false)
    }

    fun getTagsOfRequest() =
        getTagsFrom(et_tags_of_request, TagType.DEFAULT) + getTagsFrom(et_money_of_request, TagType.MONEY)

    private fun getTagsFrom(textView: TextView, tagType: TagType) = textView.text.toString()
        .trim()
        .split(" ")
        .filter(String::isNotBlank)
        .map { Tag(it, tagType) }
}