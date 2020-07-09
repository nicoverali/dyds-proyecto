package model.async.wikipedia;

import model.Word;
import model.async.IWordQueryAsyncListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WikipediaApiQueryAsyncTest {

    @Mock WikipediaApi mockedApi;
    @Mock IWordQueryAsyncListener mockedQueryListener;
    @Mock Call<Word> mockedCall;
    @InjectMocks WikipediaApiQueryAsync testSubject;

    @Captor ArgumentCaptor<Callback<Word>> callbackCaptor;

    @BeforeEach
    void setupApi(){
        when(mockedApi.getTerm(any())).thenReturn(mockedCall);
    }

    @Test
    void getMeaningWord_apiSuccess_shouldCallOnSuccessOfListener(@Mock Word testWord) {
        testSubject.getMeaningWord("test", mockedQueryListener);

        verify(mockedCall, atLeastOnce()).enqueue(callbackCaptor.capture());

        callbackCaptor.getValue().onResponse(mockedCall, Response.success(testWord));
        verify(mockedQueryListener).onSuccess(testWord);
    }

    @Test
    void getMeaningWord_apiSuccessWithNull_shouldCallOnFailureOfListener() {
        testSubject.getMeaningWord("test", mockedQueryListener);
        verify(mockedCall, atLeastOnce()).enqueue(callbackCaptor.capture());

        callbackCaptor.getValue().onResponse(mockedCall, Response.success(null));
        verify(mockedQueryListener).onFailure();
    }

    @Test
    void getMeaningWord_apiFailure_shouldCallOnFailureOfListener(@Mock Throwable someError) {
        testSubject.getMeaningWord("test", mockedQueryListener);
        verify(mockedCall, atLeastOnce()).enqueue(callbackCaptor.capture());

        callbackCaptor.getValue().onFailure(mockedCall, someError);
        verify(mockedQueryListener).onFailure();
    }
}