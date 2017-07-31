package com.example.nitini.bjj_video_organizer_v1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nitini.bjj_video_organizer_v1.data.BjjVideoContract;

/**
 * Created by nitini on 7/20/17.
 */

public class BjjVideoAdapter extends RecyclerView.Adapter<BjjVideoAdapter.LinkViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public BjjVideoAdapter(Context mContext) {this.mContext = mContext;}

    @Override
    public BjjVideoAdapter.LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.link_layout, parent, false);

        return new LinkViewHolder(view);
    }

    public static <T> T last(T[] array) {
        return array[array.length - 1];
    }

    @Override
    public void onBindViewHolder(BjjVideoAdapter.LinkViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(BjjVideoContract.VideoEntry._ID);
        int titleIndex = mCursor.getColumnIndex(BjjVideoContract.VideoEntry.COLUMN_TITLE);
        int linkIndex = mCursor.getColumnIndex(BjjVideoContract.VideoEntry.COLUMN_LINK);

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);
        String link = mCursor.getString(linkIndex);

        holder.itemView.setTag(id);

        holder.titleView.setText(title);
        holder.mLink = link;

        String thumbnailLink = String.format("https://img.youtube.com/vi/%s/0.jpg",last(link.split("/")));
        Glide.with(holder.thumbnailImage.getContext())
                .load(thumbnailLink)
                .into(holder.thumbnailImage);


    }

    @Override
    public int getItemCount() {

        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }



    class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String mLink;
        TextView titleView;
        ImageView thumbnailImage;

        public LinkViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.video_title);
            thumbnailImage = (ImageView) itemView.findViewById(R.id.youtube_thumbnail);
            thumbnailImage.setOnClickListener(this);
            titleView.setOnClickListener(this);
            //itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (v.getId() == titleView.getId()) {
                Intent startVideoDetailsActivity = new Intent(mContext, VideoDetails.class);
                startVideoDetailsActivity.putExtra("videoTitle", titleView.getText());
                startVideoDetailsActivity.putExtra("link", mLink);
                startVideoDetailsActivity.putExtra("thumbnail",  String.format("https://img.youtube.com/vi/%s/0.jpg",last(mLink.split("/"))));
                mContext.startActivity(startVideoDetailsActivity);
            } else if (v.getId() == thumbnailImage.getId()) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mLink)));
            }

        }

    }
}
