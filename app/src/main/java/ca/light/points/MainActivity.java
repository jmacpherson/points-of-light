package ca.light.points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import ca.light.points.databinding.ActivityMainBinding;
import ca.light.points.viewmodels.MainViewModel;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setModel(mViewModel);
    }
}
