import java.time.LocalDate;
import java.time.LocalTime;

public class Presence {
    private Integer workerID;
    private LocalDate date;
    private LocalTime arrival; 
    private LocalTime departure;

    public Presence(Integer workerID, LocalDate date, LocalTime arrival) {
        this.workerID = workerID;
        this.date = date;
        this.arrival = arrival;
    }

    public Presence(Integer workerID, LocalDate date, LocalTime arrival, LocalTime departure) {
        this.workerID = workerID;
        this.date = date;
        this.arrival = arrival;
        this.departure = departure;
    }

    public Integer getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Integer workerID) {
        this.workerID = workerID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "Presence [arrival=" + arrival + ", date=" + date + ", departure=" + departure + ", workerID=" + workerID
                + "]";
    }

    
    
}
