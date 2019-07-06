package com.vkram2711.usadba.models

data class User(val login: String, val password: String, val permission: String, val name: String){
    constructor() : this("", "", "", "default")
}