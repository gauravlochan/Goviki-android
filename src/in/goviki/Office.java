package in.goviki;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Office implements Serializable{

    private static final long serialVersionUID = 1L;

    int id;
    public String address;
    public String name;
    
    @SerializedName("lat")
    public Double latitude;

    @SerializedName("lon")
    public Double longitude;
}
