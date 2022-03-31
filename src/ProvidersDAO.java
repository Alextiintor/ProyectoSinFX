import java.util.HashMap;

public class ProvidersDAO implements Persistable<Provider> {
    private HashMap<Integer, Provider> providersList = new HashMap<Integer, Provider>();

    @Override
    public Provider add(Provider obj) {
        if(this.providersList.get(obj.getId())!=null){
            return null;
        } else {
            this.providersList.put(obj.getId(), obj);
            return obj;
        }
    }

    @Override
    public Provider delete(int id) {
        return this.providersList.remove(id);
    }

    @Override
    public Provider search(int id) {
        return this.providersList.get(id);
    }

    @Override
    public HashMap<Integer, Provider> getMap() {
        return this.providersList;
    }

    
}
