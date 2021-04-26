package com.yur.retrofittest

import com.google.gson.annotations.SerializedName

data class UserModel(
    var data: UserModelData,
    var support: UserModelSupport

)

data class UserModelData (
    var id: Int,
    var email: String,

    var first_name: String,


    var last_name: String,
    var avatar: String
    )

data class UserModelSupport (
    var url: String,
    var text: String
    )



data class UserListModel(
    var page: Int,
    var data: List<UserModelData>
)

data class PostUserModel(
        var name : String?,
        var job : String?,
        var id: Int? = 0,
        var createdAt: String? = ""
)