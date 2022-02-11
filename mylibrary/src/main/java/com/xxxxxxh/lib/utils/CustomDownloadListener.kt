package com.xxxxxxh.lib.utils

interface CustomDownloadListener {
    fun progress(progress:Int)
    fun downloadDone(filePath:String)
    fun downloadError(e:String)
}