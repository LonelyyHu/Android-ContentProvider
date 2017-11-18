package com.lonelyyhu.exercise.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hulonelyy on 2017/11/18.
 */

public class MyContract {

    public static final String USER_CONTENT = "users";

    public static final class UserColumns implements BaseColumns{

        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String PHONE = "phone";
    }

    public static final String CONTENT_AUTHORITY = "com.lonelyyhu.exercise.myContentProvider";

    public static final Uri USER_CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
            .authority(CONTENT_AUTHORITY)
            .appendPath(USER_CONTENT)
            .build();

}
