package in.goviki;

import java.io.Serializable;

public class Task implements Serializable{

    private static final long serialVersionUID = 1L;

    int id;
    public String title;
    public String body;
    
    private Office[] offices;
    
    public String getOffice() {
        if ((offices != null) && (offices.length>0)) {
            return offices[0].name;
        }
        return "";
    }
    
    public String getAddress() {
        if ((offices != null) && (offices.length>0)) {
            return offices[0].address;
        }
        return "";
    }

    
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

