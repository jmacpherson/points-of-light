package ca.light.points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import ca.light.points.databinding.ActivityMainBinding;
import ca.light.points.fragments.GalleryFragment;
import ca.light.points.fragments.NavigationManager;
import ca.light.points.providers.Repository;
import ca.light.points.viewmodels.MainViewModel;

import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private NavigationManager mNavigationManager;

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        PointsOfLightApplication.getApp().getComponent().inject(this);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.init(mRepository);

        binding.setModel(mViewModel);

        if(mNavigationManager == null) {
            mNavigationManager = new NavigationManager(getSupportFragmentManager(), R.id.fragment_placeholder);
        }

        mNavigationManager.navigateTo(GalleryFragment.builder());
    }

    @Override
    public void onBackPressed() {
        if(mNavigationManager.getBackStackCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
