package ca.light.points.models;

public class Dimensions {
    private float mHeight;
    private float mWidth;
    Edge mLongestEdge;

    public Dimensions(float height, float width) {
        mHeight = height;
        mWidth = width;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public Edge getLongestEdge() {
        if(mLongestEdge == null) {
            mLongestEdge = mHeight > mWidth ? Edge.HEIGHT : Edge.WIDTH;
        }

        return mLongestEdge;
    }

    public float getLongestEdgeLength() {
        if(getLongestEdge().equals(Edge.HEIGHT)) {
            return mHeight;
        } else {
            return mWidth;
        }
    }

    public float getShortestEdgeLength() {
        if(getLongestEdge().equals(Edge.HEIGHT)) {
            return mWidth;
        } else {
            return mHeight;
        }
    }

    public boolean canContain(Dimensions otherDimensions) {
        return getHeight() >= otherDimensions.getHeight() && getWidth() >= otherDimensions.getWidth();
    }

    @Override
    public boolean equals(Object otherDimensionsObject) {
        if(!(otherDimensionsObject instanceof Dimensions)) {
            return false;
        } else {
            Dimensions otherDimensions = (Dimensions) otherDimensionsObject;
            return Math.round(getHeight()) == Math.round(otherDimensions.getHeight())
                    && Math.round(getWidth()) == Math.round(otherDimensions.getWidth());
        }
    }

    public float calculateAspectRatio() {
        return getHeight() / getWidth();
    }

    public enum Edge {
        HEIGHT, WIDTH
    }
}
