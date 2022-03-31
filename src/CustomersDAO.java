import java.util.HashMap;

public class CustomersDAO implements Persistable<Customer>{

    private HashMap<Integer, Customer> customerList = new HashMap<Integer, Customer>() ;

    @Override
    public Customer add(Customer obj) {
        if(this.customerList.get(obj.getId())!=null){
            return null;
        } else{
            this.customerList.put(obj.getId(), obj);
            return obj;
        }
    }

    @Override
    public Customer delete(int id) {
        return this.customerList.remove(id);
    }

    @Override
    public Customer search(int id) {
        return this.customerList.get(id);
    }

    @Override
    public HashMap<Integer, Customer> getMap() {
        return this.customerList;
    }

}
