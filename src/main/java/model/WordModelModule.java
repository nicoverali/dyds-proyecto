package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.async.IWordQueryAsync;
import model.async.wikipedia.WikipediaApi;
import model.async.wikipedia.WikipediaApiQueryAsync;
import model.async.wikipedia.WikipediaWordDeserializer;
import model.cache.IWordCache;
import model.cache.WordCacheSQLite;
import model.sql.ISQLiteDB;
import model.sql.SQLiteDB;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WordModelModule {

    private static final String DB_NAME = "dictionary";

    private static WordModelModule instance;
    private IWordModel wordModel;

    private WordModelModule() {
        ISQLiteDB db = new SQLiteDB(DB_NAME);
        IWordCache cache = new WordCacheSQLite(db);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Word.class, new WikipediaWordDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WikipediaApi wikiAPI = retrofit.create(WikipediaApi.class);

        IWordQueryAsync async = new WikipediaApiQueryAsync(wikiAPI);

        wordModel =  new WordModel(cache, async);
    }

    public static WordModelModule getInstance() {
        if(instance == null) {
            instance =  new WordModelModule();
        }
        return instance;
    }

    public IWordModel getWordModel(){
        return wordModel;
    }

}
