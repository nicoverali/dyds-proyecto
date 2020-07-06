package model.query.wikipedia;

import model.Word;
import model.query.IInfoQueryAsync;
import model.query.IInfoQueryListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;
import java.util.Set;

public class WikipediaApiQueryAsync implements IInfoQueryAsync {

    private final WikipediaApi wikipediaApi;

    public WikipediaApiQueryAsync(WikipediaApi wikipediaApi){
        this.wikipediaApi = wikipediaApi;
    }

    @Override
    public void getMeaningWord(String term, IInfoQueryListener listener) {
        wikipediaApi.getTerm(term).enqueue(new Callback<Word>() {

            public void onResponse(Call<Word> call, Response<Word> wordResponse) {
                notifyListener(wordResponse.body(), listener);
            }

            public void onFailure(Call<Word> call, Throwable t) {
                // TODO What should we do on failure ?
            }
        });
    }

    private void notifyListener(Word word, IInfoQueryListener listener){
        if(listener!=null){
            listener.onWordResult(word);
        }
    }
}
