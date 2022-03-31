import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DAO_NotWorking<T extends Identificable> {
    // private HashMap<Integer, T> map = new HashMap<Integer,T>();

    // @Override
    // public T add(T obj) {
    //     if(map.get(obj.getID())!=null){
    //         return null;
    //     } else {
    //         map.put(obj.getID(),obj);
    //         return obj;
    //     }
    // }

    // @Override
    // public T delete(int id) {
    //     return map.remove(id);
    // }

    // @Override
    // public T search(int id) {
    //     return map.get(id);
    // }

    // @Override
    // public HashMap<Integer, T> getMap() {
    //     return map;
    // }

    // public void save() throws IOException{
    //     ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productes.dat"));
    //     System.out.println("Productos Guardados!");
    //     oos.writeObject(this.map);
    //     oos.close();
    // }

    // public void load() throws IOException{
    //     System.out.println("Cargando Productos...");
    //     try {
    //         ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productes.dat"));
    //         try {
    //             this.map = (HashMap<Integer, T>)ois.readObject();
    //             ois.close();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //         System.out.println("Productos Cargados");
    //     } catch (Exception e) {
    //         System.out.println("No existe el archivo, no se pueden cargar.");
    //     }
        
        
    // }
}
