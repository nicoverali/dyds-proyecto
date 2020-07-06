package model;

import model.query.IInfoQuery;
import model.query.IInfoQueryAsync;
import model.query.IInfoQueryListener;
import model.storage.IInfoStorage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordModel implements IWordModel {
    private IInfoStorage cacheStorage;
    private IInfoQuery cacheQuery;
    private IInfoQueryAsync wikiQuery;
    private Word lastUpdateWord;
    private IWordModelListener listener;

    public WordModel(IInfoStorage cacheSt,IInfoQuery cacheQ, IInfoQueryAsync wikiQ){
        this.cacheStorage = cacheSt;
        this.cacheQuery = cacheQ;
        this.wikiQuery = wikiQ;
    }

    @Override
    public Word getWord() {
        return lastUpdateWord;
    }

    @Override
    public void setListener(IWordModelListener listener) {
        this.listener = listener;
    }

    @Override
    public void searchWord(String term) {

        Word word = cacheQuery.getMeaningWord(term);

        if(existWord(word) && checkWordDateOk(word.getDate())){
            lastUpdateWord = word;
            notifyListener();
        }
        else{

            wikiQuery.getMeaningWord(term, new IInfoQueryListener() {
                @Override
                public void onWordResult(Word word) {
                    lastUpdateWord = word;

                    if(hasMeaning(word)) {
                        updateCacheStorage(word);
                    }

                    notifyListener();
                }
            });

        }
    }

    private boolean existWord (Word word){
        return word!=null;
    }

    // Habria que saber que sucede cuando recibo de la api una busqueda sin resultado.
    // meaning es igual a "" ?
    private boolean hasMeaning(Word word){
        return !word.getMeaning().equals("");
    }

    private boolean checkWordDateOk(Date date){
        Date yesterday = getYesterdayDate();

        return date.after(yesterday);
    }

    private void updateCacheStorage(Word word){
        cacheStorage.saveWord(word);
    }

    private void notifyListener(){
        if(listener!=null){
            listener.didUpdateWord();
        }
    }

    private Date getActualDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = sdf.format(timestamp);
        return new Date(date);
    }

    private Date getYesterdayDate(){
        Date yesterday = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdf.format(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        try {
            yesterday = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return yesterday;
    }
}
