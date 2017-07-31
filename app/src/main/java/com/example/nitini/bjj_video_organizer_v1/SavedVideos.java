package com.example.nitini.bjj_video_organizer_v1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.content.Loader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nitini.bjj_video_organizer_v1.data.BjjVideoContract;

public class SavedVideos extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = SavedVideos.class.getSimpleName();
    private static final int VIDEO_LOADER_ID = 0;

    RecyclerView mRecyclerView;
    private BjjVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_videos);



        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        if(receivedAction.equals(Intent.ACTION_SEND)){
            Bundle extras = receivedIntent.getExtras();

            String videoTitle = extras.getString(Intent.EXTRA_SUBJECT).replaceAll("Watch \"","").replaceAll("\" on YouTube","");
            String videoLink = extras.getString(Intent.EXTRA_TEXT);
            Log.d(TAG, String.format("Title: %s, Link %s", videoTitle, videoLink));

            ContentValues contentValues  = new ContentValues();
            contentValues.put(BjjVideoContract.VideoEntry.COLUMN_TITLE, videoTitle);
            contentValues.put(BjjVideoContract.VideoEntry.COLUMN_LINK, videoLink);

            Uri uri = getContentResolver().insert(BjjVideoContract.VideoEntry.CONTENT_URI,
                    contentValues);

            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
        }

        mAdapter = new BjjVideoAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewVideos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int) viewHolder.itemView.getTag();

                String stringId = Integer.toString(id);
                Uri uri = BjjVideoContract.VideoEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                getContentResolver().delete(uri, null, null);

                getLoaderManager().restartLoader(VIDEO_LOADER_ID, null, SavedVideos.this);
            }
        }).attachToRecyclerView(mRecyclerView);




        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);


        //IMPLEMENT SENDING TO YOUTUBE APP HERE
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/")));
            }
        });

        getLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(VIDEO_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mVideoData = null;

            @Override
            protected void onStartLoading() {
                if (mVideoData != null) {
                    deliverResult(mVideoData);
                } else {
                    forceLoad();
                }
            }


            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(BjjVideoContract.VideoEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            BjjVideoContract.VideoEntry._ID);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to async load data");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mVideoData = data;
                super.deliverResult(data);
            }

        };

    }

    public void launchClickedLink(View view) {

        Log.d(TAG, "WE BOUT TO IMPLEMENT");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
