package com.example.nitini.bjj_video_organizer_v1.data;

/**
 * Created by nitini on 7/16/17.
 */

import android.net.Uri;
import android.provider.BaseColumns;

public class BjjVideoContract {

    public static final String AUTHORITY = "com.example.nitini.bjj_video_organizer_v1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_VIDEOS = "videos";

    public static final class VideoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEOS).build();
        public static final String TABLE_NAME = "videos";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_TITLE = "title";

    }

}
