package ca.light.points.providers;

import java.io.IOException;

import ca.light.points.models.ApiResponse;
import ca.light.points.models.Dimensions;

public interface PhotoProvider {
    ApiResponse loadPage(Dimensions screenDimensions) throws IOException;
}
