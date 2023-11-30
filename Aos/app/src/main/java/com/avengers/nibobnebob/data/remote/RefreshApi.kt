package com.avengers.nibobnebob.data.remote

import com.avengers.nibobnebob.data.model.request.RefreshTokenRequest
import com.avengers.nibobnebob.data.model.response.BaseResponse
import com.avengers.nibobnebob.data.model.response.NaverLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApi {

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest) : Response<BaseResponse<NaverLoginResponse>>
}