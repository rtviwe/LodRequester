package com.example.roman.lodaddplaction.data

import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.model.*
import io.reactivex.Flowable

object MockRequestProvider : RequestProvider {

    private val user1 = User(
        "Yakut",
        "qwerty12345",
        "https://www.gessato.com/wp-content/uploads/2016/03/human-by-yann-arthus-bertrand-gessato-30.jpg",
        100.0,
        100.0
    )

    private val user2 = User(
        "Mitzvah Fair",
        "qwerty12345",
        "http://pages.ramaz.org/2015/SchwartzN/Mitzvah%20Fair/Person-Donald-900x1080.jpg",
        69.69,
        69.69
    )

    private val listOfRequests = Flowable.just<List<RequestWithTags>>(
        listOf(
            RequestWithTags(
                Request("Настроить принтер", "Ничего не рОБОТАЕТ", Dormitory.M4, user1),
                listOf(
                    Tag("Компухтер", TagType.DEFAULT),
                    Tag("Компухтер", TagType.DEFAULT),
                    Tag("Компухтер", TagType.DEFAULT),
                    Tag("Компухтер", TagType.DEFAULT),
                    Tag("Компухтер", TagType.DEFAULT),
                    Tag("Компухтер", TagType.DEFAULT)
                )
            ),
            RequestWithTags(
                Request("Подать на сигареты", "Просто нужно курнутб", Dormitory.GORNYAK2, user2),
                listOf(Tag("Сигареты", TagType.DEFAULT), Tag("150", TagType.MONEY))
            ),
            RequestWithTags(
                Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                listOf()
            ),
            RequestWithTags(
                Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                listOf()
            ),
            RequestWithTags(
                Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                listOf()
            )
        )
    )

    override fun getRequests(): Flowable<List<RequestWithTags>> = listOfRequests
}