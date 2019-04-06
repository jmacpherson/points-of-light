package ca.light.points.fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ca.light.points.MainActivity;
import ca.light.points.R;
import ca.light.points.databinding.FragmentLightboxBinding;
import ca.light.points.models.Photo;
import ca.light.points.viewmodels.MainViewModel;

public class LightboxFragment extends DialogFragment {

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

        binding.setModel(mViewModel);
        binding.setHandler(new UiEventHandler() {
            @Override
            public void closeLightbox(View view) {
                getActivity().onBackPressed();
            }

            @Override
            public void showPhotoDescription(View view) {
                mViewModel.showPhotoDescriptionIcon();
            }

            @Override
            public void hidePhotoDescription(View view) {
                mViewModel.hidePhotoDescriptionIcon();
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

        TextView photoDescriptionView = rootView.findViewById(R.id.lb_photo_description);
        String description = !TextUtils.isEmpty(mViewModel.selectedPhoto.getValue().description)
                ? mViewModel.selectedPhoto.getValue().description
                : "";

        photoDescriptionView.setText(Html.fromHtml(description));
        photoDescriptionView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface UiEventHandler {
        void closeLightbox(View view);

        void showPhotoDescription(View view);

        void hidePhotoDescription(View view);
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
