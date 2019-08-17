package com.countryinformation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryInfo implements Parcelable {

    public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
        @Override
        public CountryInfo createFromParcel(Parcel in) {
            return new CountryInfo(in);
        }

        @Override
        public CountryInfo[] newArray(int size) {
            return new CountryInfo[size];
        }
    };
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("population")
    @Expose
    private long population;
    @SerializedName("flag")
    @Expose
    private String flag;

    public CountryInfo() {
    }


    public CountryInfo(String name, String capital, String region, long population, String flag) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.flag = flag;
    }

    protected CountryInfo(Parcel in) {
        name = in.readString();
        capital = in.readString();
        region = in.readString();
        population = in.readLong();
        flag = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(capital);
        parcel.writeString(region);
        parcel.writeLong(population);
        parcel.writeString(flag);
    }
}
