package net.basicmodel.net

import net.basicmodel.model.DataEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestService {
    @GET("appcar/getCategoryThumb.php?page=0&ca_pic_limit=100&ca_limit=100")
    fun getData(): Call<ArrayList<DataEntity>>

    @GET("app{type}/getCategoryThumb.php?page=0&ca_pic_limit=100&ca_limit=100")
    fun getData2(@Path("type") type: String): Call<ArrayList<DataEntity>>
}