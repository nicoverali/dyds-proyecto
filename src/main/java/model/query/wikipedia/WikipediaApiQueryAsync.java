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
    public void getMeaning(String term, IInfoQueryListener listener) {
        wikipediaApi.getTerm(term).enqueue(new Callback<String>() {

            public void onResponse(Call<String> call, Response<String> response) {
                Word word = analizeResponseAndCreateWord(response, term);
                notifyListener(word, listener);
            }

            public void onFailure(Call<String> call, Throwable t) {
                // TODO What should we do on failure ?
            }
        });
    }

    private Word analizeResponseAndCreateWord(Response<String> callResponse, String term){
        Word word = null;

        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        JsonObject pages = query.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement meaning = page.get("extract");

        if (meaning != null) {
            word = createWordObject(term, meaning.getAsString().replace("\\n", "\n"));
        }

        return word;

    }

    private Word createWordObject(String term, String extract){
        Word toRet = new Word();
        toRet.setTerm(term);
        toRet.setMeaning(extract);
        return toRet;
    }

    private void notifyListener(Word word, IInfoQueryListener listener){
        if(listener!=null){
            listener.onWordResult(word);
        }
    }
}
