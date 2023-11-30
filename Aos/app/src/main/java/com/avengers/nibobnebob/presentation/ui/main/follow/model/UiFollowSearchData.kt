package com.avengers.nibobnebob.presentation.ui.main.follow.model

data class UiFollowSearchData(
    val nickName: String = "",
    val region: String = "",
    val onRootClickListener: (String) -> Unit
)
