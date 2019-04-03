package ca.light.points;

import android.app.Application;

import ca.light.points.di.DaggerPointsOfLightComponent;
import ca.light.points.di.PointsOfLightComponent;
import ca.light.points.di.PointsOfLightModule;

public class PointsOfLightApplication extends Application {

    private static PointsOfLightApplication sApplication;
    private PointsOfLightComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        component = DaggerPointsOfLightComponent.builder()
                .pointsOfLightModule(new PointsOfLightModule(this)).build();
    }

    public static PointsOfLightApplication getApp() {
        return sApplication;
    }

    public PointsOfLightComponent getComponent() {
        return component;
    }
}
