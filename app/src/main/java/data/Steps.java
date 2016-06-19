package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Tobias on 15-06-2016.
 */
@IgnoreExtraProperties
public class Steps {
    public ArrayList<Step> results;

    public static class Step implements Parcelable {
        public String method;

        public Step() {

        }

        public Step(Parcel source) {
            method = source.readString();
        }

        public Step(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(method);
        }

        public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
            @Override
            public Step createFromParcel(Parcel source) {
                return new Step(source);
            }

            @Override
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };
    }
}