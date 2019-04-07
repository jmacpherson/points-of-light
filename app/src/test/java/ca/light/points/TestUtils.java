package ca.light.points;

import android.text.TextUtils;

import mockit.Mock;
import mockit.MockUp;

public class TestUtils {

    public static void mockTextUtils() {
        new MockUp<TextUtils>() {
            @Mock
            public boolean isEmpty(CharSequence str) {
                return str == null || str.equals("");
            }
        };
    }
}
