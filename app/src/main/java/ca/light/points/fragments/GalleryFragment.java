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
    private LinearLayoutManager mLayoutManager;

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

    private void setupUi(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.photos);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoAdapter(this, mViewModel.photos);
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

    public interface UiEventHandler {
        void loadNextPage(View view);
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
