package ca.light.points.di;

import javax.inject.Singleton;

import ca.light.points.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {PointsOfLightModule.class})
public interface PointsOfLightComponent {
    void inject(MainActivity mainActivity);
}
