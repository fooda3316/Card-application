package com.example.mycards.models;
import android.os.Parcel;
import android.os.Parcelable;

public class MyResponse implements Parcelable {
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    protected MyResponse(Parcel in) {
        response = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyResponse> CREATOR = new Creator<MyResponse>() {
        @Override
        public MyResponse createFromParcel(Parcel in) {
            return new MyResponse(in);
        }

        @Override
        public MyResponse[] newArray(int size) {
            return new MyResponse[size];
        }
    };
}
