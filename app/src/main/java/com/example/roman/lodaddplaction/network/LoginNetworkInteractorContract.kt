package com.example.roman.lodaddplaction.network

import io.reactivex.Single

interface LoginNetworkInteractorContract {

    fun login(body: LoginRequestDto): Single<LoginResponseDto>
}