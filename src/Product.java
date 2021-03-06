import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Locale.Category;

public class Product implements Serializable, Identificable, Comparable<Product> {
    private static final long serialVerionUID = 12323151345l;
    private int idProduct;
    private String name;
    private Float price;
    private Integer stock;
    private LocalDate catalogStartDate;
    private LocalDate catalogFinishDate;

    private static Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
    private static NumberFormat numberFormatter = NumberFormat.getNumberInstance(localitzacioFormat);
    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);

    public Product(int idProduct, String name, Float price, int stock, LocalDate catalogStartDate, LocalDate catalogFinishDate) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.catalogStartDate = catalogStartDate;
        this.catalogFinishDate = catalogFinishDate;
    }
    
    public int getId() {
        return idProduct;
    }

    public void setId(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getCatalogStartDate(){
        return this.catalogStartDate;
    }

    public void setCatalogStartDate(LocalDate date){
        this.catalogStartDate = date;
    }

    public LocalDate getCatalogFinishDate(){
        return this.catalogFinishDate;
    }

    public void setCatalogFinishDate(LocalDate date){
        this.catalogFinishDate = date;
    }

    @Override
    public String toString() {
        return "Product [idProduct=" + numberFormatter.format(idProduct)  + ", name=" + name + ", price=" + currencyFormatter.format(price) + ", stock=" + numberFormatter.format(stock) + ", catalogFinishDate=" + catalogFinishDate + ", catalogStartDate=" + catalogStartDate +"]";
    }

    public void putStock(int stock){
        this.stock = this.stock+stock;
    }

    public void takeStock(int stock) throws StockInsuficientException{
        if(stock>this.stock){
            throw new StockInsuficientException("No hay suficiente Stock");
        } else {
            this.stock = this.stock-stock;
        }
    }

    public void showDiscontinued(LocalDate date){
        
    }

    @Override
    public boolean equals(Object obj){
        if(obj!= null || obj.getClass() != this.getClass()){
            return false;
        }

        Product product = (Product) obj;
        
        if(!this.name.equals(product.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int getID() {
        return getId();
    }

    @Override
    public int compareTo(Product o) {
        if(this.idProduct > o.idProduct){
            return 1;
        } else if (this.idProduct < o.idProduct){
            return -1;
        } else {
            return 0;
        }
    }


}
