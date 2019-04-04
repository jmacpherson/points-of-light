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
    public ApiResponse loadPage() throws IOException {
        Gson gson = new Gson();
        URL photosApi = new URL(ApiConstants.API_HOST + ApiConstants.ENDPOINT_PHOTOS + "&" + ApiConstants.PARAMETER_KEY + BuildConfig.API_KEY);

        HttpURLConnection connection = (HttpURLConnection) photosApi.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("consumer_key", BuildConfig.API_KEY);

        InputStreamReader isr = new InputStreamReader(connection.getInputStream());

        ApiResponse response = gson.fromJson(isr, ApiResponse.class);

        return response;
    }
}
