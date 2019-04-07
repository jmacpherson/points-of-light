package ca.light.points.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ca.light.points.MainActivity;
import ca.light.points.R;
import ca.light.points.adapters.PhotoAdapter;
import ca.light.points.databinding.FragmentGalleryBinding;
import ca.light.points.models.Photo;
import ca.light.points.viewmodels.MainViewModel;

public class GalleryFragment extends Fragment {

    private static final String TAG = GalleryFragment.class.getSimpleName();

    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Observable.OnPropertyChangedCallback mOrientationChangedCallback;
    private Observable.OnPropertyChangedCallback mPhotosAddedCallback;

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

        binding.setModel(mViewModel);
        binding.setHandler(new UiEventHandler() {
            @Override
            public void loadNextPage(View view) {
                mViewModel.loadPage();
            }
        });
        setupUi(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showActionBar();
        setListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        unsetListeners();
    }

    private void setupUi(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.photos);

        int layoutDirection = mViewModel.orientation.get() == Configuration.ORIENTATION_PORTRAIT
                ? RecyclerView.HORIZONTAL
                : RecyclerView.VERTICAL;
        mLayoutManager = new LinearLayoutManager(getContext(), layoutDirection, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoAdapter(mViewModel.photos.get(), new OnPhotoClickListener() {
            @Override
            public void onPhotoClick(Photo selectedPhoto) {
                mViewModel.selectPhoto(selectedPhoto);
                ((MainActivity) getActivity()).getNavigationManager().navigateTo(LightboxFragment.builder());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE
                    || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if(!mViewModel.showNextButton.get()
                            && mLayoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                        mViewModel.showPageButton();
                    } else if(mViewModel.showNextButton.get()
                            && mLayoutManager.findLastVisibleItemPosition() != mAdapter.getItemCount() - 1) {
                        mViewModel.hidePageButton();
                    }
                }
            }
        });
    }

    private void setListeners() {
        mViewModel.orientation.addOnPropertyChangedCallback(getOrientationChangedCallback());
        mViewModel.photos.addOnPropertyChangedCallback(getPhotosAddedCallback());
    }

    private void unsetListeners() {
        mViewModel.orientation.removeOnPropertyChangedCallback(getOrientationChangedCallback());
        mViewModel.photos.removeOnPropertyChangedCallback(getPhotosAddedCallback());
    }

    private Observable.OnPropertyChangedCallback getOrientationChangedCallback() {
        if(mOrientationChangedCallback == null) {
            mOrientationChangedCallback = new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    Log.i(TAG, "Orientation: " + mViewModel.orientation.get());
                    if(mViewModel.photos.get().isEmpty()) {
                        mViewModel.loadPage();
                    }
                }
            };
        }

        return mOrientationChangedCallback;
    }

    private Observable.OnPropertyChangedCallback getPhotosAddedCallback() {
        if(mPhotosAddedCallback == null) {
            mPhotosAddedCallback = new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    Log.i(TAG, "Photos added: " + mViewModel.photos.get().size());
                    mAdapter.notifyInserted();
                }
            };
        }

        return mPhotosAddedCallback;
    }

    public interface UiEventHandler {
        void loadNextPage(View view);
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(Photo selectedPhoto);
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
