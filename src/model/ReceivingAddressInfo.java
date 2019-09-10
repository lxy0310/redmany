package model;

/**
 * Created by HY on 2018/2/1 0001.
 */
public class ReceivingAddressInfo {

    public int Id;
    public String Account_id;
    public String Region_province_id;
    public String Region_city_id;
    public String Region_country_id;
    public String Consignee;
    public String Address;
    public String Mobile;
    public String Telephone;
    public String Email;
    public String Post_code;
    public String Add_time;
    public String Is_default;
    public String Region;
    public int state;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAccount_id() {
        return Account_id;
    }

    public void setAccount_id(String account_id) {
        Account_id = account_id;
    }

    public String getRegion_province_id() {
        return Region_province_id;
    }

    public void setRegion_province_id(String region_province_id) {
        Region_province_id = region_province_id;
    }

    public String getRegion_city_id() {
        return Region_city_id;
    }

    public void setRegion_city_id(String region_city_id) {
        Region_city_id = region_city_id;
    }

    public String getRegion_country_id() {
        return Region_country_id;
    }

    public void setRegion_country_id(String region_country_id) {
        Region_country_id = region_country_id;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPost_code() {
        return Post_code;
    }

    public void setPost_code(String post_code) {
        Post_code = post_code;
    }

    public String getAdd_time() {
        return Add_time;
    }

    public void setAdd_time(String add_time) {
        Add_time = add_time;
    }

    public String getIs_default() {
        return Is_default;
    }

    public void setIs_default(String is_default) {
        Is_default = is_default;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "ReceivingAddressInfo{" +
                "Id=" + Id +
                ", Account_id='" + Account_id + '\'' +
                ", Region_province_id='" + Region_province_id + '\'' +
                ", Region_city_id='" + Region_city_id + '\'' +
                ", Region_country_id='" + Region_country_id + '\'' +
                ", Consignee='" + Consignee + '\'' +
                ", Address='" + Address + '\'' +
                ", Mobile=" + Mobile +
                ", Telephone='" + Telephone + '\'' +
                ", Email='" + Email + '\'' +
                ", Post_code='" + Post_code + '\'' +
                ", Add_time='" + Add_time + '\'' +
                ", Is_default='" + Is_default + '\'' +
                ", Region='" + Region + '\'' +
                ", state=" + state +
                '}';
    }
}
