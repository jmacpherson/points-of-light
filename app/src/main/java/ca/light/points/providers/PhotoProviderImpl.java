package ca.light.points.providers;

public class PhotoProviderImpl implements PhotoProvider {

    private static PhotoProviderImpl sInstance;

    public static PhotoProviderImpl getInstance() {
        if(sInstance == null) {
            sInstance = new PhotoProviderImpl();
        }

        return sInstance;
    }

    @Override
    public void loadPage() {

    }
}
