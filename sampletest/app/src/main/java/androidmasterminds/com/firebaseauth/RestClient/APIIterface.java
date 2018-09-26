package androidmasterminds.com.firebaseauth.RestClient;

import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIIterface {


    @FormUrlEncoded
    @POST("searchWithPagination")
    Call<JsonElement> loadChanges(
                                  @FieldMap HashMap<String,String> mMap);
}
