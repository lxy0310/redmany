package common;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import common.utils.*;
import page.Page;

import javax.servlet.http.HttpServletResponse;

public class BaiduLocation {
    public final static Gson sGson = new Gson();

    public static boolean checkPos(String lat, String lon) {
        if (TextUtils.isEmpty(lat)) {
            return false;
        }
        if (TextUtils.isEmpty(lon)) {
            return false;
        }
        try {
            double _lat = Double.parseDouble(lat.trim());
            double _lon = Double.parseDouble(lon.trim());
            if (Math.abs(_lat) > 90) {
                return false;
            }
            if (Math.abs(_lon) > 180) {
                return false;
            }
            if (_lat == 0 && _lon == 0) {
                return false;
            }
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public static void saveLocation(BaiduLocation.LocationData data, HttpServletResponse resp) {
        CookieHelper.saveCookie(resp, Page.LOC_POS, data.getLatLon(), false);
        CookieHelper.saveCookie(resp, Page.LOC_CITY, SafeString.escape(data.getCity()), false);
        CookieHelper.saveCookie(resp, Page.LOC_ADDRESS_SHORT, SafeString.escape(data.getShortName()), false);
        CookieHelper.saveCookie(resp, Page.LOC_ADDRESS, SafeString.escape(data.getAddress()), false);
    }

    public static LocationData getLocationByIp(String ip) {
        LocationData data = new LocationData();
        String api = "http://api.map.baidu.com/location/ip?coor=gcj02&ak=" + APPConfig.BAIDU_KEY;
        if (ip == null || "127.0.0.1".equals(ip) || ip.startsWith("192.168.")) {

        } else {
            api += "&ip=" + ip;
        }

        String json = HttpUtils.get(api);

        LocationIp info = null;
        try {
            info = sGson.fromJson(json, LocationIp.class);
        } catch (Throwable e) {
        }
        if (info != null && info.content != null) {
            AddressDetail address_detail = info.content.address_detail;
            Point point = info.content.point;
            if (point != null) {
                data.statu = 1;
                data.lat = point.lat;
                data.lon = point.lon;
            }
            if (address_detail != null) {
                try {
                    data.statu++;
                    data.city = DataHelper.toString(address_detail.city, "");
                    data.province = DataHelper.toString(address_detail.province, "");
                    data.address = data.province;
                    if (!TextUtils.equals(data.city, data.province)) {
                        data.address += data.city;
                    }
                    data.district = DataHelper.toString(address_detail.district, "");
                    data.address += data.district;
                    data.town = DataHelper.toString(address_detail.town, "");
                    data.address += data.town;
                    //street
                    data.street = DataHelper.toString(address_detail.street, "");
                    data.street_number = DataHelper.toString(address_detail.street_number, "");
                    data.address += data.street;
                    data.address += data.street_number;
                    if (data.city != null) {
                        data.city = data.city.replace("市", "");
                    }
                } catch (Throwable e) {

                }
            }
        }
        data.makeShortName();
        return data;
    }

    public static LocationData getLocationByPos(String lat, String lon) {
        LocationData data = new LocationData();
        data.lat = lat;
        data.lon = lon;
        String api = "http://api.map.baidu.com/geocoder/v2/?extensions_poi=null&pois=0&callback=renderReverse&location=" + lat + "," + lon + "&output=json&pois=1&ak=" +
                APPConfig.BAIDU_KEY;
        String json = HttpUtils.get(api);
        LocationPos info = null;
        try {
            if (json.startsWith("renderReverse&&renderReverse(")) {
                json = json.substring("renderReverse&&renderReverse(".length(), json.length() - 1);
            }
            System.out.println(json);
            info = sGson.fromJson(json, LocationPos.class);
        } catch (Throwable e) {
        }
        if (info != null && info.result != null) {
            try {
                AddressDetail address_detail = info.result.addressComponent;
                data.statu = 0;
                data.city = DataHelper.toString(address_detail.city, "");
                data.province = DataHelper.toString(address_detail.province, "");
                data.address = DataHelper.toString(info.result.formatted_address, "");
                if (TextUtils.isEmpty(data.address)) {
                    data.address = data.province;
                    if (!TextUtils.equals(data.city, data.province)) {
                        data.address += data.city;
                    }
                    data.district = DataHelper.toString(address_detail.district, "");
                    data.address += data.district;
                    data.town = DataHelper.toString(address_detail.town, "");
                    data.address += data.town;
                    //street
                    data.street = DataHelper.toString(address_detail.street, "");
                    data.street_number = DataHelper.toString(address_detail.street_number, "");
                    data.address += data.street;
                    data.address += data.street_number;
                }
                if (data.city != null) {
                    data.city = data.city.replace("市", "");
                }
            } catch (Throwable e) {
                //
            }
        }
        data.makeShortName();
        return data;
    }

    private static class LocationPos {
        int status;
        LocationResult result;
    }

    private static class LocationResult {
        String formatted_address;
        AddressDetail addressComponent;
    }

    public static class LocationData {
        private String city;
        private String province;
        private String district;
        private String town;
        private String street;
        private String street_number;
        private String shortName;
        private String address;
        private String lat;
        private String lon;
        public int statu = -1;

        public String getShortName() {
            return shortName;
        }

        public void makeShortName() {
            shortName = DataHelper.toString(district, "");
            shortName += DataHelper.toString(street, "");
            shortName += DataHelper.toString(street_number, "");
            if (TextUtils.isEmpty(shortName)) {
                if (TextUtils.isEmpty(address)) {
                    shortName = city;
                } else {
                    shortName = address.replace(province, "");
                }
            }
        }

        public String getCity() {
            return DataHelper.toString(city, "");
        }

        public String getProvince() {
            return DataHelper.toString(province, "");
        }

        public String getDistrict() {
            return DataHelper.toString(district, "");
        }

        public String getTown() {
            return DataHelper.toString(town, "");
        }

        public String getStreet() {
            return DataHelper.toString(street, "");
        }

        public String getStreet_number() {
            return DataHelper.toString(street_number, "");
        }

        public String getAddress() {
            return DataHelper.toString(address, "");
        }

        public String getLat() {
            return DataHelper.toString(lat, "");
        }

        public String getLatLon() {
            if (TextUtils.isEmpty(lat) && TextUtils.isEmpty(lon)) {
                return "";
            }
            return DataHelper.toString(lat, "") + "," + DataHelper.toString(lon, "");
        }

        public String getLon() {
            return DataHelper.toString(lon, "");
        }

        public int getStatu() {
            return statu;
        }
    }


    private static class LocationIp {
        String address;
        int status;
        LocationIpContent content;
    }

    private static class LocationIpContent {
        String address;
        Point point;
        AddressDetail address_detail;
    }

    private static class AddressDetail {
        String city;
        int city_code;
        String district;
        String province;
        String town;
        String street;
        String street_number;
    }

    private static class Point {
        @SerializedName("y")
        String lat;
        @SerializedName("x")
        String lon;
    }

}
