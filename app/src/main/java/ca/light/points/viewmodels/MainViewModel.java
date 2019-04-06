package ca.light.points.viewmodels;

import java.util.ArrayList;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;
import ca.light.points.models.Photo;
import ca.light.points.providers.Repository;

public class MainViewModel extends ViewModel {
    public ObservableField<Boolean> showProgress = new ObservableField<>(false);
    public ObservableField<Boolean> showNextButton = new ObservableField<>(false);
    public MutableLiveData<ArrayList<Photo>> photos = new MutableLiveData<>(new ArrayList<Photo>());

    private Repository mRepository;

    private Dimensions mDimensions;
    private int page = 1;

    public void init(Repository repository) {
        mRepository = repository;
    }

    public void setDimensions(Dimensions screenDimensions) {
        mDimensions = screenDimensions;
        loadPage();
    }

    public void loadPage() {
        showProgressIndicator();
        showPageButton();
        mRepository.loadPage(page, mDimensions).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                hideProgressIndicator();
                hidePageButton();
                ObservableField<ApiResponse> response = (ObservableField<ApiResponse>) sender;
                photos.postValue(response.get().photos);
                page = response.get().current_page + 1;

            }
        });
    }

    public void showProgressIndicator() {
        showProgress.set(true);
    }

    public void hideProgressIndicator() {
        showProgress.set(false);
    }

    public void showPageButton() {
        showNextButton.set(true);
    }

    public void hidePageButton() {
        showNextButton.set(false);
    }
}
