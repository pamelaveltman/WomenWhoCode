package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shehba.shahab on 10/17/15.
 */
public class FeaturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static OnItemClickListener listener;
    private List<Object> items;
    private final int TOPIC = 0, TOPIC_HEADER = 1;

    public FeaturesAdapter(List<Object> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        FeaturesAdapter.listener = listener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TOPIC:
                View viewTopic = inflater.inflate(R.layout.item_feature, parent, false);
                viewHolder = new ViewHolderTopic(viewTopic);
                break;
            case TOPIC_HEADER:
                View viewTopicHeader = inflater.inflate(R.layout.item_topic_header, parent, false);
                viewHolder = new ViewHolderTopicHeader(viewTopicHeader);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TOPIC:
                ViewHolderTopic viewHolderTopic = (ViewHolderTopic) holder;
                configureViewHolderTopic(viewHolderTopic, position);
                break;
            case TOPIC_HEADER:
                ViewHolderTopicHeader viewHolderTopicHeader = (ViewHolderTopicHeader) holder;
                configureViewHolderTopicHeader(viewHolderTopicHeader, position);
                break;
        }

    }

    private void configureViewHolderTopicHeader(ViewHolderTopicHeader viewHolderTopicHeader, int position) {
        TextView tvTopicHeader = viewHolderTopicHeader.tvTopicHeader;
        String header = (String) items.get(position);
        tvTopicHeader.setText(header);
    }

    private void configureViewHolderTopic(ViewHolderTopic viewHolderTopic, int position) {
        Feature feature = (Feature) items.get(position);

        TextView tvFeatureTitle = viewHolderTopic.tvFeatureTitle;
        ImageView ivFeatureImage = viewHolderTopic.ivFeatureImage;
        CardView cvFeature = viewHolderTopic.cvFeature;

        // Insert the model data into each of the view items
        String title = feature.getTitle();
        String imageUrl = feature.getImageUrl();

        // set color!
        int color = Color.parseColor(String.valueOf(feature.getHexColor()));
        cvFeature.setCardBackgroundColor(color);

        // set title
        tvFeatureTitle.setText(title);

        // set image
        Context context = ivFeatureImage.getContext();
        ivFeatureImage.setImageResource(0); // clear image, to avoid memory leak
        if (title.contains("Recommend")) { // this may not be accurate - only for the recommended piece
            ivFeatureImage.setImageResource(R.drawable.ic_plus);
        } else {
            Picasso.with(context)
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .resize(75, 75)
                    .centerCrop()
                    .into(ivFeatureImage);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Feature) {
            return TOPIC;
        } else if (items.get(position) instanceof String) {
            return TOPIC_HEADER;
        }
        return -1;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public static class ViewHolderTopic extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFeatureTitle) public TextView tvFeatureTitle;
        @Bind(R.id.ivFeatureImage) public ImageView ivFeatureImage;
        @Bind(R.id.cvFeature) public CardView cvFeature;

        public ViewHolderTopic(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    public static class ViewHolderTopicHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTopicHeader) public TextView tvTopicHeader;

        public ViewHolderTopicHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}



