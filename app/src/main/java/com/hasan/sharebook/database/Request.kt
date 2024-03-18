package com.hasan.sharebook.database

data class Request(val from: String?, var to:String?, val bookId:String?){
    constructor() : this(null, null, null)
}
