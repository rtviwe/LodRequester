package com.example.roman.lodaddplaction.data

import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.model.*
import io.reactivex.Flowable

object MockRequestProvider : RequestProvider {

    private val user = User("Yakut", "qwerty12345",
            "https://www.gessato.com/wp-content/uploads/2016/03/human-by-yann-arthus-bertrand-gessato-30.jpg")

    private val listOfRequests = Flowable.just<List<RequestWithTags>>(listOf(
            RequestWithTags(
                    Request("Настроить принтер", "Ничего не рОБОТАЕТ", Dormitory.M4, user),
                    listOf(Tag("Компухтер", TagType.DEFAULT, 0))),
            RequestWithTags(
                    Request("Подать на сигареты", "Просто нужно курнутб", Dormitory.GORNYAK2, user),
                    listOf(Tag("Сигареты", TagType.DEFAULT, 0), Tag("150", TagType.MONEY, 0))),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf()),
            RequestWithTags(
                    Request("Что-то сделать", "Что-то там", Dormitory.M1, null),
                    listOf())
    ))

    override fun getRequests(): Flowable<List<RequestWithTags>> = listOfRequests
}