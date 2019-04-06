package ca.light.points.providers;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;
import ca.light.points.providers.network.NetworkRequestRunner;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private Context mApplicationContext;
    private NetworkRequestRunner mNetworkRequestRunner;
    private PhotoProvider mPhotoProvider;

    @Inject
    public Repository(Context context, NetworkRequestRunner networkRequestRunner, PhotoProvider photoProvider) {
        mApplicationContext = context;
        mNetworkRequestRunner = networkRequestRunner;
        mPhotoProvider = photoProvider;
    }

    public ObservableField<ApiResponse> loadPage(final int page, final Dimensions screenDimensions) {
        final ObservableField<ApiResponse> result = new ObservableField<>();
        mNetworkRequestRunner.run(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiResponse response = mPhotoProvider.loadPage(page, screenDimensions);
                    result.set(response);
                } catch (IOException ex) {
                    Log.e(TAG, "Caught IOException: " + ex.getMessage());
                }
            }
        });
        return result;
    }
}
