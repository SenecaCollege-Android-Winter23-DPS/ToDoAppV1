package com.example.midtermtest_winter23;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Task implements Parcelable {

    String task;
    String date;
    boolean isUrgent;

    public Task(String task, String date, boolean isUrgent) {
        this.task = task;
        this.date = date;
        this.isUrgent = isUrgent;
    }

    protected Task(Parcel in) {
        task = in.readString();
        date = in.readString();
        isUrgent = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(task);
        parcel.writeString(date);
        parcel.writeByte((byte) (isUrgent ? 1 : 0));
    }

    @Override
    public String toString() {
        String iu = this.isUrgent? "1":"0";
        return this.task+","+this.date+","+ iu+"\n";
    }



    //go shopping,31/2/2023,1
    public static Task fromString(String taskString){
        int first = 0;
        int end = 0;
        for (int i = first; i < taskString.length();i++){
            if (taskString.charAt(i) == ','){
                end = i;
                break;
            }
        }
        String task = taskString.substring(first,end);

        first = end + 1;
        end = 0;

        for (int i = first; i < taskString.length();i++){
            if (taskString.charAt(i) == ','){
                end = i;
                break;
            }
        }
        String date = taskString.substring(first,end);

        char isurgent = taskString.charAt(end+1);
        boolean b = isurgent == '1'? true: false;
        Task newTask = new Task(task,date,b);
        return newTask;

    }



}
