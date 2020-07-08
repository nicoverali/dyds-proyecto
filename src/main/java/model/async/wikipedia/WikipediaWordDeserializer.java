package model.async.wikipedia;

import com.google.gson.*;
import model.Word;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class WikipediaWordDeserializer implements JsonDeserializer<Word> {

    @Override
    public Word deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject root = json.getAsJsonObject();
        JsonObject query = root.getAsJsonObject("query");
        JsonObject pages = query.getAsJsonObject("pages");
        String term = getTerm(query);
        String meaning = getFirstPageExtract(pages);

        return new Word(term, meaning);
    }

    private String getTerm(JsonObject query){
        JsonObject firstNormalizedTerm = query.getAsJsonArray("normalized").get(0).getAsJsonObject();
        return firstNormalizedTerm.get("from").getAsString();
    }

    private String getFirstPageExtract(JsonObject pages){
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject firstPage = first.getValue().getAsJsonObject();
        return firstPage.get("extract").getAsString();
    }

}
