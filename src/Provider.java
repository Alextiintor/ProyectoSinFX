public class Provider implements Identificable{
    private int id;
    private String name;
    private String nif;
    private String phone;

    public Provider(int id, String name, String nif, String phone) {
        this.id = id;
        this.name = name;
        this.nif = nif;
        this.phone = phone;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Provider [name=" + name + ", nif=" + nif + ", phone=" + phone + "]";
    }

    @Override
    public int getID() {
        return getId();
    }
        
}
