package controller;

import model.IWordModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DictionaryControllerTest {

    @Mock IWordModel mockedModel;
    @InjectMocks DictionaryController controller;

    @Test
    void onUserWordSearch_shouldUpdateModel() {
        String testWord = "test";
        controller.onUserWordSearch(testWord);

        verify(mockedModel).searchWord(testWord);
    }

    @Test
    void onUserWordSearch_wordIsNullOrIsEmpty_shouldNotUpdateModel(){
        controller.onUserWordSearch(null);
        controller.onUserWordSearch("");

        verify(mockedModel, never()).searchWord(any());
    }
}