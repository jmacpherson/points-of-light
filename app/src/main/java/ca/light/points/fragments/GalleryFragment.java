package ca.light.points.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ca.light.points.R;
import ca.light.points.adapters.PhotoAdapter;
import ca.light.points.databinding.FragmentGalleryBinding;
import ca.light.points.viewmodels.MainViewModel;

public class GalleryFragment extends Fragment {

    private static final String TAG = GalleryFragment.class.getSimpleName();

    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentGalleryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_gallery, container, false);

//        if(mViewModel.photos.getValue().isEmpty()) {
//            mViewModel.loadPage();
//        }
        setupUi(binding.getRoot());

        return binding.getRoot();
    }

    private void setupUi(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.photos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PhotoAdapter(this, mViewModel.photos);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static FragmentBuilder builder() {
        return new FragmentBuilder() {
            @Override
            public String getTag() {
                return TAG;
            }

            @Override
            public GalleryFragment build() {
                return new GalleryFragment();
            }
        };
    }
}
