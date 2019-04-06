package ca.light.points.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ca.light.points.MainActivity;
import ca.light.points.R;
import ca.light.points.databinding.FragmentLightboxBinding;
import ca.light.points.models.Photo;
import ca.light.points.viewmodels.MainViewModel;

public class LightboxFragment extends Fragment {

    private static final String TAG = LightboxFragment.class.getSimpleName();

    private MainViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLightboxBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_lightbox, container, false);

        binding.setHandler(new UiEventHandler() {
            @Override
            public void closeLightbox(View view) {
                getActivity().onBackPressed();
            }
        });
        setupUi(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideActionBar();
    }

    private void setupUi(final View rootView) {
        mViewModel.selectedPhoto.observe(this, new Observer<Photo>() {
            @Override
            public void onChanged(Photo photo) {
                Picasso.get().load(photo.preferredUrl).into((ImageView) rootView.findViewById(R.id.lb_photo));
            }
        });
    }

    public interface UiEventHandler {
        void closeLightbox(View view);
    }

    public static FragmentBuilder builder() {
        return new FragmentBuilder() {
            @Override
            public String getTag() {
                return TAG;
            }

            @Override
            public Fragment build() {
                return new LightboxFragment();
            }
        };
    }
}
