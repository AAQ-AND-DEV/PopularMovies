package com.example.android.popularmovies;

public class Movies {

    private String mPoster;
    private String mBackdrop;
    private String mTitle;
    private String mRating;
    private String mReleaseDate;
    private String mSynopsis;

    public Movies(){
    }

    public Movies(String poster, String backdrop, String title, String rating, String releaseDate, String synopsis) {
        this.mPoster = poster;
        this.mBackdrop = backdrop;
        this.mTitle = title;
        this.mRating = rating;
        this.mReleaseDate = releaseDate;
        this.mSynopsis = synopsis;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        this.mPoster = poster;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        this.mBackdrop = backdrop;
    }

    public String getOrigTitle() {
        return mTitle;
    }

    public void setOrigTitle(String title) {
        this.mTitle = title;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        this.mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }
}
