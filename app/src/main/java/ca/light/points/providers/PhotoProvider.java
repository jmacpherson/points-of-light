package ca.light.points.providers;

import java.io.IOException;

import ca.light.points.models.ApiResponse;

public interface PhotoProvider {
    ApiResponse loadPage() throws IOException;
}
