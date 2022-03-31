import java.time.LocalDate;
import java.util.ArrayList;

public class PresenceDAO{
    private ArrayList<Presence> presencesList = new ArrayList<Presence>();

    public Presence add(Presence obj) {
        Presence added = null;
        if(this.presencesList.size()>0){
            for(Presence presence : this.presencesList){
                if(presence.getWorkerID() == obj.getWorkerID()){
                    if(presence.getDate() == obj.getDate()){
                        added = null;
                    } else {
                        this.presencesList.add(obj);
                        added = obj;
                    }
                } else {
                    this.presencesList.add(obj);
                    added = obj;
                }
            }
        } else {
            this.presencesList.add(obj);
            added = obj;
        }
        

        return added;
    }

    public Presence delete(int id) {
        return this.presencesList.remove(id);
    }

    public Presence search(int id, LocalDate date) {
        Presence presence = null;
        for(Presence temp : this.presencesList){
            if(temp.getWorkerID() == id && temp.getDate() == date){
                presence = temp;
            }
        }

        return presence;
    }

    public ArrayList<Presence> searchDay(LocalDate date) {
        ArrayList<Presence> dayPresenceList = new ArrayList<Presence>();
        for(Presence temp : this.presencesList){
            if(temp.getDate().compareTo(date) == 0){
                dayPresenceList.add(temp);
            }
        }

        return dayPresenceList;
    }
    public ArrayList<Presence> searchId(int id) {
        ArrayList<Presence> dayPresenceList = new ArrayList<Presence>();
        for(Presence temp : this.presencesList){
            if(temp.getWorkerID() == id){
                dayPresenceList.add(temp);
            }
        }

        return dayPresenceList;
    }

    public ArrayList<Presence> getMap() {
        return this.presencesList;
    }

}
