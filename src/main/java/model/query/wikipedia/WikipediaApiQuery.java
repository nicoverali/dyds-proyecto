package model.query.wikipedia;

import model.Word;
import model.query.IInfoQuery;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class WikipediaApiQuery implements IInfoQuery {

    private static WikipediaApiQuery instance;
    private WikipediaApi wikipediaApi;
    private Word wordResponse;

    private WikipediaApiQuery(WikipediaApi wikipediaApi){
        this.wikipediaApi = wikipediaApi;
        //createWikiApiObject();
    }

    public static WikipediaApiQuery getInstance(WikipediaApi wikipediaApi){
        if(instance==null){
            instance = new WikipediaApiQuery(wikipediaApi);
        }

        return instance;
    }

    private void createWikiApiObject(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        wikipediaApi = retrofit.create(WikipediaApi.class);
    }

    @Override
    public Word getMeaning(String term) {

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Response<String> callResponse;
                    Word word = null;
                    try {
                        callResponse = wikipediaApi.getTerm(term).execute();

                        word = analizeResponseAndCreateWord(callResponse);

                        if (existWord(word)) {
                            word.setTerm(term);
                        }

                        wordResponse = word;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).join();
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return wordResponse;
    }

    private boolean existWord(Word word){
        return word!=null;
    }

    private Word analizeResponseAndCreateWord(Response<String> callResponse){
        Word word = null;

        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        JsonObject pages = query.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement extract = page.get("extract");

        if (extract != null) {
            word = createWordObject(extract.getAsString().replace("\\n", "\n"));
        }

        return word;

    }

    private Word createWordObject(String extract){
        Word toRet = new Word();
        toRet.setMeaning(extract);

        return toRet;
    }

}
