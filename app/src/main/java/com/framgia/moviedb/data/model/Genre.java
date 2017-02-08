package com.framgia.moviedb.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.framgia.moviedb.data.source.local.GenrePersistenceContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class Genre implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public Genre(Cursor cursor) {
        mId = cursor.getInt(
            cursor.getColumnIndexOrThrow(GenrePersistenceContract.GenreEntry.COLUMN_NAME_ENTRY_ID));
        mName = cursor.getString(
            cursor.getColumnIndexOrThrow(GenrePersistenceContract.GenreEntry.COLUMN_NAME_TITLE));
    }

    private Genre(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
    }

    public static final Parcelable.Creator<Genre> CREATOR
        = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
