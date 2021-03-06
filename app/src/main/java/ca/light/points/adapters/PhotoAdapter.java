package ca.light.points.adapters;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ca.light.points.R;
import ca.light.points.fragments.GalleryFragment;
import ca.light.points.models.Dimensions;
import ca.light.points.models.Photo;
import ca.light.points.utils.PhotoUtils;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private ArrayList<Photo> mPhotos;
    private GalleryFragment.OnPhotoClickListener mOnClickListener;
    private int insertionPoint = 0;

    public PhotoAdapter(ArrayList<Photo> photos, GalleryFragment.OnPhotoClickListener onClickListener) {
        mOnClickListener = onClickListener;

        mPhotos = photos;
        notifyInserted();
    }

    public void notifyInserted() {
        notifyItemInserted(insertionPoint);
        insertionPoint = mPhotos.size();
    }

    @Override
    @NonNull
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_photo, parent, false);

        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder viewHolder, final int position) {
        final Photo photo = mPhotos.get(position);
        Dimensions imageDimensions = new Dimensions(photo.height, photo.width);
        if(TextUtils.isEmpty(photo.preferredSize) && TextUtils.isEmpty(photo.preferredUrl)) {
            photo.preferredSize = PhotoUtils.getSizeToUse(imageDimensions);

            ArrayList<Photo.PhotoUrl> urls = photo.images;
            for (Photo.PhotoUrl photoUrl : urls) {
                if (!TextUtils.isEmpty(photo.preferredSize) && Integer.parseInt(photo.preferredSize) == photoUrl.size) {
                    photo.preferredUrl = photoUrl.url;
                }
            }
        }

        Dimensions imageDimensionsAtScale = PhotoUtils.calculateDimensionsAtScale(imageDimensions, photo.preferredSize);
        viewHolder.getImageView().setMinimumWidth(Math.round(imageDimensionsAtScale.getWidth()));
        viewHolder.getImageView().setMinimumHeight(Math.round(imageDimensionsAtScale.getHeight()));

        Picasso.get().load(photo.preferredUrl)
                .into(viewHolder.getImageView());
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "OnClickListener position: " + position + " photoId " + photo.id);
                mOnClickListener.onPhotoClick(photo);
            }
        });
    }

    @Override
    public void onViewRecycled(final PhotoViewHolder viewHolder) {
        viewHolder.cleanup();
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo);
        }

        ImageView getImageView() {
            return imageView;
        }

        void cleanup() {
            Picasso.get().cancelRequest(imageView);
            imageView.setImageDrawable(null);
        }
    }
}
