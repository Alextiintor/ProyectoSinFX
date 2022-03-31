import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Person {
    private static int numberOfPersons;
    
    private int personID;
    private String dni;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private int telephone;
    private Address address;

    public Person(int personID, String dni, String name, String lastName) {
        this.personID = personID;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        numberOfPersons++;
    }

    public Person(int personID, String dni, String name, String lastName, LocalDate birthday, String email,
            int telephone, Address address) {
        this.personID = personID;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        numberOfPersons++;
    }

    public boolean isValid(Person p){
        boolean isValid = true;

        if(p.getName().length()<1){
            isValid = false;
        } 

        if(p.getLastName().length()<1){
            isValid = false;
        }

        if(p.getDNI().length()<9){
            isValid = false;
        }

        return isValid;
    }

    public static long diferenciaEdad(Person p1, Person p2){
        return (int)ChronoUnit.YEARS.between(p1.birthday, LocalDate.now())-(int)ChronoUnit.YEARS.between(p2.birthday, LocalDate.now());
    }

    public int getAge(){
        return (int)ChronoUnit.YEARS.between(this.birthday, LocalDate.now());
    }

    public static int getNumberOfPersons() {
        return numberOfPersons;
    }

    public int getPersonID() {
        return personID;
    }
    public void setPersonID(int personID) {
        this.personID = personID;
    }
    public String getDNI() {
        return dni;
    }
    public void setDNI(String dni) {
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
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getTelephone() {
        return telephone;
    }
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        int edat = getAge();
        return "Persona [address=" + address + ", lastName=" + lastName + ", birthday=" + birthday + ", dni="
                + dni + ", email=" + email + ", personID=" + personID + ", name=" + name + ", telephone=" + telephone + ", edat=" + edat + "]";
    }

}
