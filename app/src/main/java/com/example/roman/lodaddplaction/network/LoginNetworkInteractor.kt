package com.example.roman.lodaddplaction.network

import com.bumptech.glide.load.HttpException
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class LoginNetworkInteractor : LoginNetworkInteractorContract {

    override fun login(body: LoginRequestDto): Single<LoginResponseDto> =
            when (Pair(body.name, body.password)) {
                Pair("admin", "12345") -> Single
                        .just(LoginResponseDto("1235-fsdfs-123123-asdfasd"))

                else -> Single.error(HttpException(403))

            }
                    .timeout(1500, TimeUnit.MILLISECONDS)
}