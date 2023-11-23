package com.avengers.nibobnebob.data.remote

import com.avengers.nibobnebob.data.model.request.EditMyInfoRequest
import com.avengers.nibobnebob.data.model.response.BaseResponse
import com.avengers.nibobnebob.data.model.response.MyDefaultInfoResponse
import com.avengers.nibobnebob.data.model.response.MyInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface MyPageApi {
    @GET("api/user/details")
    suspend fun getMyInfo(): Response<BaseResponse<MyInfoResponse>>

    @GET("api/user")
    suspend fun getMyDefaultInfo(): Response<BaseResponse<MyDefaultInfoResponse>>

    @PUT("api/user")
    suspend fun editMyInfo(
        @Body data: EditMyInfoRequest
    ): Response<Unit>

}