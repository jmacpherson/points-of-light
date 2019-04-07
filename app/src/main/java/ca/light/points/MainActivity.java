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
import android.view.View;
import android.view.ViewTreeObserver;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
                updateDimensions();
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

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void updateDimensions() {
        View mainContent = findViewById(R.id.main_content);
        mViewModel.setDimensions(new Dimensions(mainContent.getHeight(), mainContent.getWidth()));
    }
}
