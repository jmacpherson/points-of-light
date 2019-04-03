package ca.light.points.providers;

import android.content.Context;

import javax.inject.Inject;

public class Repository {

    private Context mApplicationContext;
    private PhotoProvider mPhotoProvider;

    @Inject
    public Repository(Context context, PhotoProvider photoProvider) {
        mApplicationContext = context;
        mPhotoProvider = photoProvider;
    }

    public void loadPage() {
        mPhotoProvider.loadPage();
    }
}
