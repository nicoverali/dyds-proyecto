package Model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class WikiApiQueryAsync implements IInfoQueryAsync {
    private WikiApi wikiApi;
    private IWikiListener listener;

    public WikiApiQueryAsync(WikiApi wikiApi){
        this.wikiApi = wikiApi;
    }

    public void setListener(IWikiListener listener){
        this.listener = listener;
    }

    @Override
    public void getMeaning(String term) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response<String> callResponse;
                Word word = null;
                try {
                    callResponse = wikiApi.getTerm(term).execute();

                    word = analizeResponseAndCreateWord(callResponse);

                    if (existWord(word)) {
                        word.setTerm(term);
                    }

                    notifyListener(word);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

    private void notifyListener(Word word){
        if(listener!=null){
            listener.didUpdateSearch(word);
        }
    }
}
