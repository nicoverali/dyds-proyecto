package model.async.wikipedia;

import model.Word;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaApi {

    @GET("api.php?format=json&action=query&prop=extracts&exlimit=1&explaintext&exintro")
    Call<Word> getTerm(@Query("titles") String term);

}
