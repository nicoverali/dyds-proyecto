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
            notifySuccessToListener();
        }
        else{

            wordAsyncQuery.getMeaningWord(term, new IWordQueryAsyncListener() {
                @Override
                public void onSuccess(Word word) {
                    lastUpdateWord = word;
                    wordCache.save(word);
                    notifySuccessToListener();
                }

                @Override
                public void onFailure() {
                    lastUpdateWord = null;
                    notifyFailureToListener();
                }
            });

        }
    }

    private boolean existWord (Word word){
        return word!=null;
    }

    private void notifySuccessToListener(){
        if(listener!=null){
            listener.onWordUpdate();
        }
    }

    private void notifyFailureToListener(){
        if(listener!=null){
            listener.onWordNotFound();
        }
    }
}
