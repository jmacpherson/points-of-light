package ca.light.points.providers;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ca.light.points.BuildConfig;
import ca.light.points.constants.ApiConstants;
import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;
import ca.light.points.utils.PhotoUtils;

public class PhotoProviderImpl implements PhotoProvider {

    private static final String TAG = PhotoProviderImpl.class.getSimpleName();
    private static PhotoProviderImpl sInstance;

    public static PhotoProviderImpl getInstance() {
        if(sInstance == null) {
            sInstance = new PhotoProviderImpl();
        }

        return sInstance;
    }

    @Override
    public ApiResponse loadPage(int page, Dimensions screenDimensions) throws IOException {
        Gson gson = new Gson();
        String urlString = ApiConstants.API_HOST
                + ApiConstants.ENDPOINT_PHOTOS
                + "&" + ApiConstants.PARAMETER_KEY + BuildConfig.API_KEY
                + "&" + ApiConstants.IMAGE_SIZE_KEY + PhotoUtils.getImageSizeArgs(screenDimensions)
                + "&" + ApiConstants.PAGE_KEY + page;

        URL photosApi = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) photosApi.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");

        InputStreamReader isr = new InputStreamReader(connection.getInputStream());

        ApiResponse response = gson.fromJson(isr, ApiResponse.class);

        isr.close();

        return response;
    }
}
