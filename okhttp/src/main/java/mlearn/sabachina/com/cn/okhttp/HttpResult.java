package mlearn.sabachina.com.cn.okhttp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 网络请求的默认返回
 *
 * @author htyuan
 */
public class HttpResult implements  Parcelable {
    public static int RESULT_OK = 200;

    private int mStat = RESULT_OK;

    private String mMessage;

    private String errtime;

    public HttpResult() {
    }

    public static final Creator<HttpResult> CREATOR = new Creator<HttpResult>() {
        @Override
        public HttpResult createFromParcel(Parcel in) {
            return new HttpResult(in);
        }

        @Override
        public HttpResult[] newArray(int size) {
            return new HttpResult[size];
        }
    };

    public void setState(int stat) {
        mStat = stat;
    }

    public int getState() {
        return mStat;
    }

    public boolean isSuccess() {
        return mStat == 0 || mStat == 200;
    }

    public static boolean isSuccess(HttpResult result) {
        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "mStat=" + mStat +
                ", mMessage='" + mMessage + '\'' +
                ", errtime='" + errtime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStat);
        dest.writeString(this.mMessage);
        dest.writeString(this.errtime);
    }

    protected HttpResult(Parcel in) {
        this.mStat = in.readInt();
        this.mMessage = in.readString();
        this.errtime = in.readString();
    }

}