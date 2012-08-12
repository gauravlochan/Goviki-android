package in.goviki;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HackyStuff {
    
    public static Task[] getTaskArray() {
        Task[] items = { 
                new Task(1, "Apply for a Passport", "Regional Passport Office", "Marathahalli",
                        "Requirements: 2 address proofs \n" +
                        "Process: Apply with a smile"), 
                new Task(2, "Apply for drivers license", "RTO", "Koramangala"), 
                new Task(3, "Register at Open Hack", "Sheraton Hotel", "Malleshwaram"), 
                new Task(4, "Register a property", "BDA", "KP West"), 
                new Task(5, "Score some weed", "Sheraton Hotel", "Malleshwaram"), 
            };

        return items;
    }

    // Used to create a task
    public void postTask() {
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("[task][body]", "12345"));
        nameValuePairs.add(new BasicNameValuePair("task[title]", "Gaurav title"));
        
        String tasksUrl = "http://goviki.herokuapp.com/tasks/";
        RESTHelper.postData(tasksUrl, nameValuePairs);
    }
 
}
