import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductsDAO implements Persistable<Product> {

    private HashMap<Integer, Product> productList = new HashMap<Integer, Product>();

    @Override
    public Product add(Product obj) {
        if(this.productList.get(obj.getId())!=null){
            return null;
        } else {
            this.productList.put(obj.getId(), obj);
            return obj;
        }
    }

    @Override
    public Product delete(int id) {
        return this.productList.remove(id);
    }

    @Override
    public Product search(int id) {
        return this.productList.get(id);
    }

    @Override
    public HashMap<Integer, Product> getMap() {
        return productList;
    }

    public ArrayList<String> getDiscontinuedProducts(LocalDate date){
        ArrayList<String> result = new ArrayList<>();

        for (Product object : this.productList.values()) {
            if(object.getCatalogFinishDate().compareTo(date) <= 0){
                long daysAgo = ChronoUnit.DAYS.between(object.getCatalogFinishDate(), date);
                result.add(object.getName()+" discontinued "+ daysAgo +" days ago");
            }
        }

        return result;
    }

    public void save() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productes.dat"));
        System.out.println("Productos Guardados!");
        oos.writeObject(this.productList);
        oos.close();
    }

    public void load() throws IOException{
        System.out.println("Cargando Productos...");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productes.dat"));
            try {
                this.productList = (HashMap<Integer, Product>)ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Productos Cargados");
        } catch (Exception e) {
            System.out.println("No existe el archivo, no se pueden cargar.");
        }
    }
}
