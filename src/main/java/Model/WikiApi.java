package Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiApi {

    @GET("api.php?format=json&action=query&prop=extracts&exlimit=1&explaintext&exintro")
    Call<String> getTerm(@Query("titles") String term);

}
