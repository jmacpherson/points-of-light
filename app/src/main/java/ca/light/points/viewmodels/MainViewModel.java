package ca.light.points.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import ca.light.points.providers.Repository;

public class MainViewModel extends ViewModel {
    public ObservableField<Boolean> showProgress = new ObservableField<>(false);

    private Repository mRepository;

    public void init(Repository repository) {
        mRepository = repository;
    }

    public void loadPage() {
        mRepository.loadPage();
    }
}
