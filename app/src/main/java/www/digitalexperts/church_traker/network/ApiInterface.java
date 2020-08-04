package www.digitalexperts.church_traker.network;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("search.php")
    @FormUrlEncoded
    Call<ResponseBody> searchresults(@Field("search") String q);

    @POST("events.php")
    @FormUrlEncoded
    Call<ResponseBody> getingpastors(@Field("cid") String q);

    @POST("getfolders.php")
    @FormUrlEncoded
    Call<ResponseBody> getingteachings(@Field("folder") String q);

    @POST("folderitems.php")
    @FormUrlEncoded
    Call<ResponseBody> getfoldercontents(@Field("table") String x,@Field("folder") String q);



}
