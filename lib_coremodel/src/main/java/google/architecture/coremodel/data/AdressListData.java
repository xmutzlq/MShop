package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AdressListData {
    @SerializedName("list")
    private List<AddressListItem> list;

    public List<AddressListItem> getList() {
        return list;
    }

    public void setList(List<AddressListItem> list) {
        this.list = list;
    }

    public static class AddressListItem implements Parcelable {
        @SerializedName("address_id")
        private String address_id;
        @SerializedName("user_id")
        private String user_id;
        @SerializedName("consignee")
        private String consignee;
        @SerializedName("email")
        private String email;
        @SerializedName("country")
        private String country;
        @SerializedName("province_id")
        private String province_id;
        @SerializedName("province_name")
        private String province_name;
        @SerializedName("city_id")
        private String city_id;
        @SerializedName("city_name")
        private String city_name;
        @SerializedName("district_id")
        private String district_id;
        @SerializedName("district_name")
        private String district_name;
        @SerializedName("town_id")
        private String town_id;
        @SerializedName("town_name")
        private String town_name;
        @SerializedName("address")
        private String address;
        @SerializedName("zipcode")
        private String zipcode;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("is_default")
        private int is_default;
        @SerializedName("is_pickup")
        private int is_pickup;

        public boolean isSelected;

        protected AddressListItem(Parcel in) {
            address_id = in.readString();
            user_id = in.readString();
            consignee = in.readString();
            email = in.readString();
            country = in.readString();
            province_id = in.readString();
            province_name = in.readString();
            city_id = in.readString();
            city_name = in.readString();
            district_id = in.readString();
            district_name = in.readString();
            town_id = in.readString();
            town_name = in.readString();
            address = in.readString();
            zipcode = in.readString();
            mobile = in.readString();
            is_default = in.readInt();
            is_pickup = in.readInt();
            isSelected = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(address_id);
            dest.writeString(user_id);
            dest.writeString(consignee);
            dest.writeString(email);
            dest.writeString(country);
            dest.writeString(province_id);
            dest.writeString(province_name);
            dest.writeString(city_id);
            dest.writeString(city_name);
            dest.writeString(district_id);
            dest.writeString(district_name);
            dest.writeString(town_id);
            dest.writeString(town_name);
            dest.writeString(address);
            dest.writeString(zipcode);
            dest.writeString(mobile);
            dest.writeInt(is_default);
            dest.writeInt(is_pickup);
            dest.writeByte((byte) (isSelected ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AddressListItem> CREATOR = new Creator<AddressListItem>() {
            @Override
            public AddressListItem createFromParcel(Parcel in) {
                return new AddressListItem(in);
            }

            @Override
            public AddressListItem[] newArray(int size) {
                return new AddressListItem[size];
            }
        };

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(String district_id) {
            this.district_id = district_id;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getTown_id() {
            return town_id;
        }

        public void setTown_id(String town_id) {
            this.town_id = town_id;
        }

        public String getTown_name() {
            return town_name;
        }

        public void setTown_name(String town_name) {
            this.town_name = town_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public int getIs_pickup() {
            return is_pickup;
        }

        public void setIs_pickup(int is_pickup) {
            this.is_pickup = is_pickup;
        }
    }
}
