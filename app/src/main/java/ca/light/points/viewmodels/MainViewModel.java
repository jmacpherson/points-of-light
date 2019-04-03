package ca.light.points.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public ObservableField<Boolean> showProgress = new ObservableField<>(false);
}
