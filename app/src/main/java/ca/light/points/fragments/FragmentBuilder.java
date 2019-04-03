package ca.light.points.fragments;

import androidx.fragment.app.Fragment;

public interface FragmentBuilder {
    String getTag();

    Fragment build();
}
