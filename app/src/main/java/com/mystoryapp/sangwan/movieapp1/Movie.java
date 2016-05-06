package com.mystoryapp.sangwan.movieapp1;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

//import com.example.sangwan.popular_movie_app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amit Sangwan on 28-04-2016.
 */
public class Movie implements Parcelable {


    private long mId;
    private String mTitle;
    private String mPoster;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;
    private String mBackdrop;

    public Movie(long mId, String mTitle, String mPoster, String mOverview, String mUserRating, String mReleaseDate, String mBackdrop) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mPoster = mPoster;
        this.mOverview = mOverview;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
        this.mBackdrop = mBackdrop;
    }
    private Movie(){

    }
    protected Movie(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mUserRating = in.readString();
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public String getmPosterUrl(Context context){
        if(mPoster != null && mPoster.isEmpty()){
            return context.getResources().getString(R.string.url_for_downloading_poster)+mPoster;
        }
        return null;
    }

    public String getReleaseDate(Context context) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        if (mReleaseDate != null && !mReleaseDate.isEmpty()) {
            try {
                Date date = inputFormat.parse(mReleaseDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                // Log.e(LOG_TAG, "The Release data was not parsed successfully: " + mReleaseDate);
                // Return not formatted date
            }
        } else {
            mReleaseDate = context.getString(R.string.release_date_missing);
        }
        return mReleaseDate;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            Movie mv = new Movie();
            mv.mId = in.readLong();
            mv.mBackdrop = in.readString();
            mv.mOverview = in.readString();
            mv.mPoster = in.readString();
            mv.mUserRating = in.readString();
            mv.mReleaseDate = in.readString();
            mv.mTitle = in.readString();
            return mv;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mOverview);
        dest.writeString(mUserRating);
        dest.writeString(mReleaseDate);
        dest.writeString(mBackdrop);
    }
}
