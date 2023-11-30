package com.avengers.nibobnebob.data.model.response

import com.google.gson.annotations.SerializedName

data class RestaurantDetailResponse(
    @SerializedName("isMy") val isMy : Boolean,
    @SerializedName("isWish") val isWish : Boolean,
    @SerializedName("restaurant_address")val address: String,
    @SerializedName("restaurant_category")val category: String,
    @SerializedName("restaurant_id")val id: Int,
    @SerializedName("restaurant_location")val location: Location,
    @SerializedName("restaurant_name")val name: String,
    @SerializedName("restaurant_phoneNumber")val phoneNumber: String,
    @SerializedName("restaurant_reviewCnt")val reviewCnt: Int,
    @SerializedName("reviews") val reviews : List<Reviews>?
)

data class Reviews(
    @SerializedName("review_id")val id: Int,
    @SerializedName("review_created_at")val createdAt : String,
    @SerializedName("review_isCarVisit")val isCarVisit: Boolean,
    @SerializedName("review_overallExperience")val overallExperience: String,
    @SerializedName("review_parkingArea")val parkingArea: Int,
    @SerializedName("review_restroomCleanliness")val restroomCleanliness: Int,
    @SerializedName("review_service")val service: Int,
    @SerializedName("review_taste")val taste: Int,
    @SerializedName("review_transportationAccessibility")val transportationAccessibility: Int,
    @SerializedName("reviewer") val reviewer : String,
)