package com.example.projekt_semestr_5.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("To jest galeria ale jej nie bedzie");



    }

    public LiveData<String> getText() {
        return mText;
    }

}
