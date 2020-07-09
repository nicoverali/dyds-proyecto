package model;

import model.async.IWordQueryAsyncListener;
import model.async.IWordQueryAsync;
import model.cache.IWordCache;

public class WordModel implements IWordModel {
    private IWordCache wordCache;
    private IWordQueryAsync wordAsyncQuery;
    private Word lastUpdateWord;
    private IWordModelListener listener;

    public WordModel(IWordCache wordCache, IWordQueryAsync wordQueryAsync){
        this.wordCache = wordCache;
        this.wordAsyncQuery = wordQueryAsync;
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

        Word word = wordCache.get(term);

        if(existWord(word)){
            lastUpdateWord = word;
            notifyListener();
        }
        else{

            wordAsyncQuery.getMeaningWord(term, new IWordQueryAsyncListener() {
                @Override
                public void onSuccess(Word word) {
                    lastUpdateWord = word;

                    if(hasMeaning(word)) {
                        wordCache.save(word);
                    }

                    notifyListener();
                }

                @Override
                public void onFailure() {

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

    private void notifyListener(){
        if(listener!=null){
            listener.didUpdateWord();
        }
    }
}
