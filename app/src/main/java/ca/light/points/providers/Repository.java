package ca.light.points.providers;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;
import ca.light.points.providers.network.NetworkRequestRunner;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private NetworkRequestRunner mNetworkRequestRunner;
    private PhotoProvider mPhotoProvider;

    @Inject
    public Repository(NetworkRequestRunner networkRequestRunner, PhotoProvider photoProvider) {
        mNetworkRequestRunner = networkRequestRunner;
        mPhotoProvider = photoProvider;
    }

    public MutableLiveData<ApiResponse> loadPage(final int page, final Dimensions screenDimensions) {
        final MutableLiveData<ApiResponse> result = new MutableLiveData<>();
        mNetworkRequestRunner.run(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiResponse response = mPhotoProvider.loadPage(page, screenDimensions);
                    result.postValue(response);
                } catch (IOException ex) {
                    Log.e(TAG, "Caught IOException: " + ex.getMessage());
                }
            }
        });
        return result;
    }
}
