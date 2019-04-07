package ca.light.points.viewmodels;

import java.util.ArrayList;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;
import ca.light.points.models.Photo;
import ca.light.points.providers.Repository;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public ObservableField<Boolean> showProgress = new ObservableField<>(false);
    public ObservableField<Boolean> showNextButton = new ObservableField<>(false);
    public ObservableField<Boolean> showPhotoDescription = new ObservableField<>(true);
    public ObservableField<ArrayList<Photo>> photos = new ObservableField<>(new ArrayList<Photo>());
    public MutableLiveData<Photo> selectedPhoto = new MutableLiveData<>(new Photo());

    private Repository mRepository;

    private Dimensions mDimensions;
    private int page = 1;

    public void init(Repository repository) {
        mRepository = repository;
    }

    public void setDimensions(Dimensions screenDimensions) {
        mDimensions = screenDimensions;
        if(photos.get().isEmpty()) {
           loadPage();
        }
    }

    public void loadPage() {
        showProgressIndicator();
        showPageButton();
        final MutableLiveData<ApiResponse> liveData = mRepository.loadPage(page, mDimensions);
        liveData.observeForever(new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse response) {
                hideProgressIndicator();
                hidePageButton();

                // remove duplicates
                ArrayList<Photo> photosReceived = response.photos;
                photosReceived.removeAll(photos.get());
                photos.get().addAll(photosReceived);

                page = response.current_page + 1;

                photos.notifyChange();
                liveData.removeObserver(this);
            }
        });
    }

    public void selectPhoto(Photo newSelectedPhoto) {
        selectedPhoto.postValue(newSelectedPhoto);
    }

    public void showProgressIndicator() {
        showProgress.set(true);
    }

    public void hideProgressIndicator() {
        showProgress.set(false);
    }

    public void showPhotoDescriptionIcon() {
        showPhotoDescription.set(true);
    }

    public void hidePhotoDescriptionIcon() {
        showPhotoDescription.set(false);
    }

    public void showPageButton() {
        showNextButton.set(true);
    }

    public void hidePageButton() {
        showNextButton.set(false);
    }
}
