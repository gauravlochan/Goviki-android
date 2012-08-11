package in.goviki;

import java.io.Serializable;

public class Task implements Serializable{

    private static final long serialVersionUID = 1L;

    int id;
    public String title;
    public String body;
    
    public String office;
    //public String address;
    //public SimpleGeoPoint location;
    
    
    public Task(int id, String name, String office, String address, String body) {
        this.id = id;
        this.title = name;
        this.body = body;
    }

    public Task(int id, String name, String office, String address) {
        this.id = id;
        this.title = name;
        this.body = "";
    }

    @Override
    public String toString() {
        return this.id + ". " + this.title;
    }
    
}

