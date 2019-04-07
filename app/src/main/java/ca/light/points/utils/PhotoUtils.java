package ca.light.points.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import ca.light.points.models.Dimensions;

public class PhotoUtils {

    private static Dimensions mScreenDimensions;
    private static HashSet<String> mSizes = new HashSet<>();
    private static String mSizeArgs = "";
    private static String mLongestEdgeLessThanLongestEdge;
    private static String mLongestEdgeLessThanShortestEdge;
    private static String mHeightLessThanLongestEdge;
    private static String mHeightLessThanShortestEdge;

    public static String getImageSizeArgs(Dimensions screenDimensions) {
        cacheScreenDimensions(screenDimensions);

        if(mSizes.isEmpty()) {
            mSizes.add(getLongestEdgeLessThanLongestEdge(screenDimensions));
            mSizes.add(getLongestEdgeLessThanShortestEdge(screenDimensions));
            mSizes.add(getHeightLessThanLongestEdge(screenDimensions));
            mSizes.add(getHeightLessThanShortestEdge(screenDimensions));

            Iterator<String> sizeIterator = mSizes.iterator();
            while(sizeIterator.hasNext()) {
                mSizeArgs = mSizeArgs + sizeIterator.next();
                if(sizeIterator.hasNext()) {
                    mSizeArgs += ",";
                }
            }
        }

        return mSizeArgs;
    }

    /**
     * TODO: Update to track preferredSize per resolution
     *
     * @param imageDimensions
     * @return
     */
    public static String getSizeToUse(Dimensions imageDimensions) {
        HashMap<String, Dimensions> viableImageDimensionsAtSize = new HashMap<>();
        String sizeToUse = "";

        Iterator<String> sizeIterator = mSizes.iterator();
        while(sizeIterator.hasNext()) {
            String size = sizeIterator.next();

            Dimensions imageDimensionsAtSize = calculateDimensionsAtScale(imageDimensions, size);
            if(mScreenDimensions.canContain(imageDimensionsAtSize)) {
                viableImageDimensionsAtSize.put(size, imageDimensionsAtSize);

                if(TextUtils.isEmpty(sizeToUse)
                    || imageDimensionsAtSize.getHeight() >= viableImageDimensionsAtSize.get(sizeToUse).getHeight()) {
                    sizeToUse = size;
                }
            }
        }


        return sizeToUse;
    }

    public static Dimensions calculateDimensionsAtScale(Dimensions imageDimensions, String sizeCode) {
        Size size = Size.findSizeByCode(sizeCode);
        Dimensions result;

        if(size.type == 'h' || imageDimensions.getLongestEdge() == Dimensions.Edge.HEIGHT) {
            float scaledWidth = getNewDimension(imageDimensions.getWidth(), imageDimensions.getHeight(), size.dimension);
            result = new Dimensions(size.dimension, scaledWidth);
        } else {
            float scaledHeight = getNewDimension(imageDimensions.getHeight(), imageDimensions.getWidth(), size.dimension);
            result = new Dimensions(scaledHeight, size.dimension);
        }

        return result;
    }

    private static float getNewDimension(float originalDimensionToDiscover, float originalDimensionKnown, float newDimensionKnown) {
        return (originalDimensionToDiscover / originalDimensionKnown) * newDimensionKnown;
    }

    private static String getLongestEdgeLessThan(float dimension) {
        String sizeArg;
        if(dimension <= Size.LONG_256.dimension) {
            sizeArg = "30";
        } else if(dimension <= Size.LONG_900.dimension) {
            sizeArg = "4";
        } else if (dimension <= Size.LONG_1080.dimension) {
            sizeArg = "1080";
        } else if (dimension <= Size.LONG_1170.dimension) {
            sizeArg = "5";
        } else if (dimension <= Size.LONG_1600.dimension) {
            sizeArg = "1600";
        } else  {
            sizeArg = "2048";
        }
        return sizeArg;
    }

    private static String getHeightLessThan(float dimension) {
        String sizeArg;
        if(dimension <= Size.HIGH_300.dimension) {
            sizeArg = "20";
        } else if(dimension <= Size.HIGH_450.dimension) {
            sizeArg = "31";
        } else if(dimension <= Size.HIGH_600.dimension) {
            sizeArg = "21";
        } else {
            sizeArg = "6";
        }
        return sizeArg;
    }

    private static void cacheScreenDimensions(Dimensions screenDimensions) {
        if(mScreenDimensions == null) {
            mScreenDimensions = screenDimensions;
        }
    }

    public static void clearCache() {
        mScreenDimensions = null;
        mSizes.clear();
        mSizeArgs = "";
        mLongestEdgeLessThanLongestEdge = null;
        mLongestEdgeLessThanShortestEdge = null;
        mHeightLessThanLongestEdge = null;
        mHeightLessThanShortestEdge = null;
    }

    private static String getLongestEdgeLessThanLongestEdge(Dimensions screenDimensions) {
        if(TextUtils.isEmpty(mLongestEdgeLessThanLongestEdge)) {
            mLongestEdgeLessThanLongestEdge = getLongestEdgeLessThan(screenDimensions.getLongestEdgeLength());
        }
        return mLongestEdgeLessThanLongestEdge;
    }

    private static String getLongestEdgeLessThanShortestEdge(Dimensions screenDimensions) {
        if(TextUtils.isEmpty(mLongestEdgeLessThanShortestEdge)) {
            mLongestEdgeLessThanShortestEdge = getLongestEdgeLessThan(screenDimensions.getShortestEdgeLength());
        }
        return mLongestEdgeLessThanShortestEdge;
    }

    private static String getHeightLessThanLongestEdge(Dimensions screenDimensions) {
        if(TextUtils.isEmpty(mHeightLessThanLongestEdge)) {
            mHeightLessThanLongestEdge = getHeightLessThan(screenDimensions.getLongestEdgeLength());
        }
        return mHeightLessThanLongestEdge;
    }

    private static String getHeightLessThanShortestEdge(Dimensions screenDimensions) {
        if(TextUtils.isEmpty(mHeightLessThanShortestEdge)) {
            mHeightLessThanShortestEdge = getHeightLessThan(screenDimensions.getShortestEdgeLength());
        }
        return mHeightLessThanShortestEdge;
    }

    enum Size {
        LONG_256    ("30", 'l', 256),
        LONG_900    ("4", 'l', 900),
        LONG_1080   ("1080", 'l', 1080),
        LONG_1170   ("5", 'l', 1170),
        LONG_1600   ("1600", 'l', 1600),
        LONG_2048   ("2048", 'l', 2048),
        HIGH_300    ("20", 'h', 300),
        HIGH_450    ("31", 'h', 450),
        HIGH_600    ("21", 'h', 600),
        HIGH_1080   ("6", 'h', 1080);

        static HashMap<String, Size> sizes = new HashMap<>();

        String code;
        char type;
        float dimension;

        Size(String code, char type, float dimension) {
            this.code = code;
            this.type = type;
            this.dimension = dimension;
        }

        static Size findSizeByCode(String code) {
            if(sizes.isEmpty()) {
                for(Size size: Size.values()) {
                    sizes.put(size.code, size);
                }
            }

            return sizes.get(code);
        }
    }
}
