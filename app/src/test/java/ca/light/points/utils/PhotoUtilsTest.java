package ca.light.points.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import ca.light.points.TestUtils;
import ca.light.points.models.Dimensions;
import mockit.integration.junit4.JMockit;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class PhotoUtilsTest {

    @Before
    public void setUp() {
        TestUtils.mockTextUtils();
    }

    @Test
    public void getImageSizeArgs_test() {
        Dimensions[] dimensionsToTest = {
                new Dimensions(1080, 1440),
                new Dimensions(1440, 1080),
                new Dimensions(2560, 1440),
                new Dimensions(1440, 2560),
                new Dimensions(1200, 1200),
                new Dimensions(3400, 2560)
        };
        String[] expectedSizeArgs = {
                "1080,1600,6",
                "1080,1600,6",
                "1600,2048,6",
                "1600,2048,6",
                "1600,6",
                "2048,6"
        };

        for(int i = 0; i < dimensionsToTest.length; i++) {
            String sizeArgs = PhotoUtils.getImageSizeArgs(dimensionsToTest[i]);
            assertEquals(expectedSizeArgs[i], sizeArgs);
            PhotoUtils.clearCache();
        }
    }

    @Test
    public void getSizeToUse_test() {
        Dimensions[] screenDimensionsToTest = {
                new Dimensions(1080, 1440),
                new Dimensions(1440, 1080),
                new Dimensions(2560, 1440),
                new Dimensions(1440, 2560),
                new Dimensions(1200, 1200),
                new Dimensions(3400, 2560)
        };
        Dimensions[] imageDimensionsToTest = {
                new Dimensions(5000, 3780),
                new Dimensions(2700, 1200),
                new Dimensions(2400, 2400)
        };
        ArrayList<String[]> expectedSizesToUse = new ArrayList<>();
        expectedSizesToUse.add(new String[] {
                "6",
                "6",
                "1600",
                "6",
                "6",
                "2048"
        });
        expectedSizesToUse.add(new String[] {
                "6",
                "6",
                "2048",
                "6",
                "6",
                "2048"
        });
        expectedSizesToUse.add(new String[] {
                "6",
                "6",
                "6",
                "6",
                "6",
                "2048"
        });

        for(int i = 0; i < imageDimensionsToTest.length; i++) {
            for(int j = 0; j < screenDimensionsToTest.length; j++) {
                PhotoUtils.getImageSizeArgs(screenDimensionsToTest[j]);
                String sizeToUse = PhotoUtils.getSizeToUse(imageDimensionsToTest[i]);
                assertEquals(expectedSizesToUse.get(i)[j], sizeToUse);
                PhotoUtils.clearCache();
            }
        }
    }

    @Test
    public void calculateDimensionsAtScale_test() {
        Dimensions[] imageDimensionsToScale = {
            new Dimensions(2480, 1080),
            new Dimensions(1080, 2048)
        };
        String[] sizes = {
            "2048", "31"
        };
        ArrayList<Dimensions[]> expectedDimensionsAtScale = new ArrayList<>();
        expectedDimensionsAtScale.add(new Dimensions[] {
            new Dimensions(2048,892),
            new Dimensions(450,196)
        });
        expectedDimensionsAtScale.add(new Dimensions[] {
            new Dimensions(1080,2048),
            new Dimensions(450,853)
        });

        for(int i = 0; i < imageDimensionsToScale.length; i++) {
            for(int j = 0; j < sizes.length; j++) {
                Dimensions imageDimensionsAtScale
                        = PhotoUtils.calculateDimensionsAtScale(imageDimensionsToScale[i], sizes[j]);

                assertEquals(expectedDimensionsAtScale.get(i)[j], imageDimensionsAtScale);
                assertEquals(imageDimensionsToScale[i].calculateAspectRatio(),
                        imageDimensionsAtScale.calculateAspectRatio(),
                        1);
            }
        }
    }
}
