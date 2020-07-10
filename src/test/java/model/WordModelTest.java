package model;

import model.async.IWordQueryAsync;
import model.async.IWordQueryAsyncListener;
import model.cache.IWordCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.rowset.CachedRowSet;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordModelTest {

    @Mock Date dateMock;

    @Mock IWordCache cacheMock;
    @Mock IWordQueryAsync queryAsyncMock;
    @Mock IWordModelListener modelListenerMock;

    @InjectMocks WordModel modelTestSubject;

    @Captor ArgumentCaptor<IWordQueryAsyncListener> asyncListenerCaptor;

    Word testWord;

    @BeforeEach
    void setupWord(){
        testWord = new Word("test", "is a test");
        testWord.setSource(1);
        testWord.setDate(dateMock);
    }

    @BeforeEach
    void setupModelListener(){
        modelTestSubject.setListener(modelListenerMock);
    }

    @Test
    void searchWord_existsOnCache_shouldReturnFromCache_notCallAsync() {
        when(cacheMock.get(testWord.getTerm())).thenReturn(testWord);

        modelTestSubject.searchWord(testWord.getTerm());

        verify(queryAsyncMock, never()).getMeaningWord(any(), any());

        // Should notify listener of new word
        verify(modelListenerMock).onWordUpdate();

        // When we ask for the new word it must be the same that cache returned
        assertEquals(testWord, modelTestSubject.getWord());

    }

    @Test
    void searchWord_notExistsOnCache_shouldCallAsync_asyncSuccess_shouldStoreOnCache(){
        // In this test cache doesn't have the word that model will look for
        when(cacheMock.get(testWord.getTerm())).thenReturn(null);

        modelTestSubject.searchWord(testWord.getTerm());

        // Model should call async query with a listener, because the word wasn´t present on cache
        verify(queryAsyncMock).getMeaningWord(matches(testWord.getTerm()), asyncListenerCaptor.capture());
        IWordQueryAsyncListener listenerGiveByModel = asyncListenerCaptor.getValue();

        // Async has the word so call model listener onSuccess
        listenerGiveByModel.onSuccess(testWord);

        verify(cacheMock).save(testWord);

        // Should notify listener and return the same word obtain from async
        verify(modelListenerMock).onWordUpdate();
        assertEquals(testWord, modelTestSubject.getWord());

    }

    @Test
    void searchWord_notExistsOnCache_shouldCallAsync_asyncFailure_shouldNotifyListenerOfFailure(){
        // In this test cache doesn't have the word that model will look for
        when(cacheMock.get(testWord.getTerm())).thenReturn(null);

        modelTestSubject.searchWord(testWord.getTerm());

        // Model should call async query with a listener, because the word wasn´t present on cache
        verify(queryAsyncMock).getMeaningWord(matches(testWord.getTerm()), asyncListenerCaptor.capture());
        IWordQueryAsyncListener listenerGiveByModel = asyncListenerCaptor.getValue();

        // Async has the word so call model listener onSuccess
        listenerGiveByModel.onFailure();

        verify(cacheMock, never()).save(any());

        // Should notify listener of failure, and last word must be null
        verify(modelListenerMock).onWordNotFound();
        assertNull(modelTestSubject.getWord());
    }
}