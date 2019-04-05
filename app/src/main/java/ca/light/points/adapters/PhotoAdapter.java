package ca.light.points.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import ca.light.points.R;
import ca.light.points.models.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<Photo> mPhotos = new ArrayList<>();

    public PhotoAdapter(Fragment parentFragment, MutableLiveData<ArrayList<Photo>> photosLiveData) {
        addPhotos(photosLiveData.getValue());

        photosLiveData.observe(parentFragment, new Observer<ArrayList<Photo>>() {
            @Override
            public void onChanged(ArrayList<Photo> photos) {
                addPhotos(photos);
            }
        });
    }

    private void addPhotos(ArrayList<Photo> photos) {
        int insertionPoint = mPhotos.size();
        mPhotos.addAll(photos);
        notifyItemInserted(insertionPoint + 1);
    }

    @Override
    @NonNull
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_photo, parent, false);

        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder viewHolder, int position) {
        Picasso.get().load(mPhotos.get(position).image_url.get(0)).into(viewHolder.getImageView());
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
    }
}
