package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class TargetToSaveModel implements Parcelable {
    double travel=0;
    double luxurios=0;
    double medical=0;
    double savings=0;
    double total=0.0;

    public TargetToSaveModel()
    {

    }
    public TargetToSaveModel(double travel,double luxurios,double medical,double savings)
    {
        this.travel=travel;
        this.luxurios=luxurios;
        this.medical=medical;
        this.savings=savings;
        this.total=travel+luxurios+medical+savings;

    }
    protected TargetToSaveModel(Parcel in) {
        travel = in.readDouble();
        luxurios = in.readDouble();
        medical = in.readDouble();
        savings = in.readDouble();
        total = in.readDouble();
    }

    public static final Creator<TargetToSaveModel> CREATOR = new Creator<TargetToSaveModel>() {
        @Override
        public TargetToSaveModel createFromParcel(Parcel in) {
            return new TargetToSaveModel(in);
        }

        @Override
        public TargetToSaveModel[] newArray(int size) {
            return new TargetToSaveModel[size];
        }
    };

    public double getTravel() {
        return travel;
    }

    public void setTravel(double trave) {
        this.travel = trave;
    }

    public double getLuxurios() {
        return luxurios;
    }

    public void setLuxurios(double luxurios) {
        this.luxurios = luxurios;
    }

    public double getMedical() {
        return medical;
    }

    public void setMedical(double medical) {
        this.medical = medical;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(travel);
        dest.writeDouble(luxurios);
        dest.writeDouble(medical);
        dest.writeDouble(savings);
        dest.writeDouble(total);
    }
}
