package model.async.wikipedia;

import model.Word;
import model.async.IWordQueryAsync;
import model.async.IWordQueryAsyncListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WikipediaApiQueryAsync implements IWordQueryAsync {

    private final WikipediaApi wikipediaApi;

    public WikipediaApiQueryAsync(WikipediaApi wikipediaApi){
        this.wikipediaApi = wikipediaApi;
    }

    @Override
    public void getMeaningWord(String term, IWordQueryAsyncListener listener) {
        wikipediaApi.getTerm(term).enqueue(new Callback<Word>() {

            public void onResponse(Call<Word> call, Response<Word> wordResponse) {
                Word result = wordResponse.body();
                if(result != null){
                    listener.onSuccess(wordResponse.body());
                } else{
                    listener.onFailure();
                }
            }

            public void onFailure(Call<Word> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
