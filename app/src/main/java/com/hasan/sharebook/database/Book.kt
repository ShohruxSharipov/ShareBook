package com.hasan.sharebook.database

import android.net.Uri

data class Book(val bookId:String?,val name:String?,val description:String?,val genre:String?,val owner:String?){
    constructor():this(null,null,null,null,null)
}
