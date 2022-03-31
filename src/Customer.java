import java.time.LocalDate;

public class Customer implements Identificable{
    private int id;
    private String dni;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public Customer(int id, String dni, String name, String lastName, LocalDate birthDate, String email, String phone) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
    }

    public int getId(){
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer [birthDate=" + birthDate + ", dni=" + dni + ", email=" + email + ", lastName=" + lastName
                + ", name=" + name + ", phone=" + phone + "]";
    }

    @Override
    public int getID() {
        return getId();
    }
    
}
