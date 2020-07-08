package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.DictionaryController;
import controller.IDictionaryController;
import model.IWordModel;
import model.Word;
import model.WordModel;
import model.query.CacheQuery;
import model.query.wikipedia.WikipediaApi;
import model.query.wikipedia.WikipediaApiQueryAsync;
import model.query.wikipedia.WikipediaWordDeserializer;
import model.storage.CacheStorage;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Word.class, new WikipediaWordDeserializer())
                        .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WikipediaApi wikiAPI = retrofit.create(WikipediaApi.class);

       IWordModel dictModel = new WordModel(CacheStorage.getInstance(), CacheQuery.getInstance(), new WikipediaApiQueryAsync(wikiAPI));
       IDictionaryController dictController = new DictionaryController(dictModel);
       IDictionaryView dictView = new DictionaryView(dictController, dictModel);

       JFrame frame = new JFrame("Online Dictionary");
       frame.setContentPane(dictView.getContentPane());
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
    }

}
