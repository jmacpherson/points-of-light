package ca.light.points.viewmodels;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ca.light.points.models.ApiResponse;
import ca.light.points.providers.Repository;

public class MainViewModel extends ViewModel {
    public ObservableField<Boolean> showProgress = new ObservableField<>(false);
    public MutableLiveData<ApiResponse> photos = new MutableLiveData<>();

    private Repository mRepository;

    public void init(Repository repository) {
        mRepository = repository;
    }

    public void loadPage() {
        mRepository.loadPage().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                photos.postValue(((ObservableField<ApiResponse>) sender).get());
            }
        });
    }
}
