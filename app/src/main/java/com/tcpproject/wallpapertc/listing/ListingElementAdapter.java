package com.tcpproject.wallpapertc.listing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tcpproject.wallpapertc.R;
import com.tcpproject.wallpapertc.model.Element;

import java.util.List;

public class ListingElementAdapter extends RecyclerView.Adapter<ListingElementAdapter.ViewHolder> {

    private List<Element> mCategories;
    private ListingElementAdapter.Callback onClickCallback;

    public ListingElementAdapter(List<Element> mCategories, ListingElementAdapter.Callback callback) {
        this.mCategories = mCategories;
        this.onClickCallback = callback;
    }

    @NonNull
    @Override
    public ListingElementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.item_element_layout, parent, false);

        ListingElementAdapter.ViewHolder viewHolder = new ListingElementAdapter.ViewHolder(heroView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingElementAdapter.ViewHolder holder, int position) {
        final Element element = mCategories.get(position);
        holder.mImageHero.setOnClickListener(view -> onClickCallback.onClickItem(element));
        Picasso.get()
                .load(element.getUrl())
                .placeholder(R.drawable.image_holder_content)
                .into(holder.mImageHero);

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageHero;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.imgItemElementContent);
        }

    }

    interface Callback {
        void onClickItem(Element Element);
    }
}
