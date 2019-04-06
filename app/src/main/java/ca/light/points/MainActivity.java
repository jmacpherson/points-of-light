package ca.light.points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import ca.light.points.databinding.ActivityMainBinding;
import ca.light.points.fragments.GalleryFragment;
import ca.light.points.fragments.NavigationManager;
import ca.light.points.models.Dimensions;
import ca.light.points.providers.Repository;
import ca.light.points.viewmodels.MainViewModel;

import android.os.Bundle;
import android.view.ViewTreeObserver;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private NavigationManager mNavigationManager;

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        PointsOfLightApplication.getApp().getComponent().inject(this);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.mainContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewModel.setDimensions(new Dimensions(binding.mainContent.getHeight(), binding.mainContent.getWidth()));
                binding.mainContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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

    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    public void showActionBar() {
        getSupportActionBar().show();
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }
}
