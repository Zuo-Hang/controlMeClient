package com.example.myapplicationnumba.entity_model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EquipmentModel implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int type;//种类
    private String Manufacturer;//厂商
    private int status;//状态

    public static final Creator<EquipmentModel> CREATOR = new Creator<EquipmentModel>() {
        @Override
        public EquipmentModel createFromParcel(Parcel in) {
            return new EquipmentModel(in);
        }

        @Override
        public EquipmentModel[] newArray(int size) {
            return new EquipmentModel[size];
        }
    };

    public EquipmentModel(long id) {
        this.id = id;
    }

    protected EquipmentModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        Manufacturer = in.readString();
        status = in.readInt();
        type = in.readInt();
    }

    @Generated(hash = 1365211370)
    public EquipmentModel(Long id, String name, int type, String Manufacturer, int status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.Manufacturer = Manufacturer;
        this.status = status;
    }

    @Generated(hash = 627607897)
    public EquipmentModel() {
    }

 

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }

        dest.writeString(name);
        dest.writeInt(status);
        dest.writeString(Manufacturer);
        dest.writeInt(type);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getManufacturer() {
        return this.Manufacturer;
    }

    public void setManufacturer(String Manufacturer) {
        this.Manufacturer = Manufacturer;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
