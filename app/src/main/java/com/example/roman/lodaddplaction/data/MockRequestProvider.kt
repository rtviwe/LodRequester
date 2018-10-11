package com.example.roman.lodaddplaction.data

import com.example.roman.lodaddplaction.models.*

object MockRequestProvider : RequestProvider {

    private val listOfRequests = mutableListOf(
            RequestModel("Помочь настроить принтер",
                    listOf(Tag("IT"), Tag("Принтеры")),
                    "Ничего не рОБОТОЕТ",
                    null,
                    UserModel("Макаров",
                            "http://old.misis.ru/portals/0/Kaf_matem/Макаров%20П.В.jpg")
            ),
            RequestModel("Подайте на сигареты",
                    listOf(Tag("150р", TagType.MONEY), Tag("Подать")),
                    "Просто нужно курнутб",
                    Dormitory.M2,
                    null
            ),
            RequestModel("Найти кошку",
                    listOf(Tag("Срочно"), Tag("7-11 этажи"), Tag("Кошка"), Tag("!!!"),
                            Tag("!!!!!!!!!!!"), Tag("Животное"), Tag("Поиски")),
                    "Вам срочно нужно искать кота",
                    Dormitory.M3,
                    UserModel("Анатолий Ямщиков",
                            "https://pp.userapi.com/c847216/v847216475/5d526/YGOIQoc-GhI.jpg")
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            ),
            RequestModel("Какая-то просьба",
                    listOf(Tag("Тэг")),
                    "Что-то там сделать",
                    null,
                    null
            )
    )

    override fun getRequests(): List<RequestModel> = listOfRequests
}