package ca.light.points.di;

import android.app.Application;
import android.content.Context;

import ca.light.points.providers.PhotoProvider;
import ca.light.points.providers.PhotoProviderImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class PointsOfLightModule {
    private Application mApplication;

    public PointsOfLightModule(Application application) {
        mApplication = application;
    }

    @Provides
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    PhotoProvider providePhotoProvider() {
        return PhotoProviderImpl.getInstance();
    }
}
