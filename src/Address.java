public class Address {
    private String city;
    private String province;
    private int postalCode;
    private String door;

    public Address(String city, String province, int postalCode, String door){
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.door = door;
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    @Override
    public String toString() {
        return "Address [postalCode=" + postalCode + ", door=" + door + ", city=" + city + ", province=" + province
                + "]";
    }

}
