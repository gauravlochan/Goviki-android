package in.goviki;

public class Task {

    public int id;

    public String name;
    
    public String office;
    public String address;
    public SimpleGeoPoint location;
    
    public String procedure;
    
    public Task(int id, String name, String office, String address, String procedure) {
        this.id = id;
        this.name = name;
        this.office = office;
        this.address = address;
        this.procedure = procedure;
        this.location = new SimpleGeoPoint("12.9833", "77.5833");
    }


    public Task(int id, String name, String office, String address) {
        this.id = id;
        this.name = name;
        this.office = office;
        this.address = address;
        this.procedure = "under construction";
        this.location = new SimpleGeoPoint("12.9833", "77.5833");
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name;
    }
    
}

