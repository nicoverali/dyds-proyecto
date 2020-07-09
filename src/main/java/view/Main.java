package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DictionaryController;
import controller.IDictionaryController;
import model.IWordModel;
import model.Word;
import model.WordModel;
import model.async.wikipedia.WikipediaApi;
import model.async.wikipedia.WikipediaApiQueryAsync;
import model.async.wikipedia.WikipediaWordDeserializer;
import model.cache.WordCacheSQLite;
import model.sql.ISQLiteDB;
import model.sql.SQLiteDB;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        ISQLiteDB db = new SQLiteDB("dictionary");

        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Word.class, new WikipediaWordDeserializer())
                        .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WikipediaApi wikiAPI = retrofit.create(WikipediaApi.class);

       IWordModel dictModel = new WordModel(new WordCacheSQLite(db), new WikipediaApiQueryAsync(wikiAPI));
       IDictionaryController dictController = new DictionaryController(dictModel);
       IDictionaryView dictView = new DictionaryView(dictController, dictModel);

       JFrame frame = new JFrame("Online Dictionary");
       frame.setContentPane(dictView.getContentPane());
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
    }

}
