package in.goviki;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

public class GovikiActivity extends Activity {
    
    String server = "http://goviki.herokuapp.com/tasks.json";
    ArrayAdapter<Task> adapter;
    ListView listView1;
    Task[] items = {};
    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<Task>(GovikiActivity.this, android.R.layout.simple_list_item_1);
        listView1.setAdapter(adapter);

        new GetTasksAsynchTask().execute();
    }
    
    public void postTask() {
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("[task][body]", "12345"));
        nameValuePairs.add(new BasicNameValuePair("task[title]", "Gaurav title"));
        
        String tasksUrl = "http://goviki.herokuapp.com/tasks/";
        RESTHelper.postData(tasksUrl, nameValuePairs);
    }
    
    
    public class GetTasksAsynchTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            adapter.clear(); // clear "old" entries (optional)
        }

        @Override
        protected String doInBackground(Void... params) {
            String json = RESTHelper.simpleGet(server);
            return json;
        }

        
        @Override
        protected void onPostExecute(String json) {
            // Add entries to list
            items = gson.fromJson(json, Task[].class);
            for (int i=0; i< items.length; i++) {
                Task item = items[i];
                adapter.add(item);
            }

            
            // Enable clicks
            listView1.setOnItemClickListener(new OnItemClickListener() {
                
                // Click handler for each list item
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                        long id) {
                    
                    // Go into task details page
                    Intent intent = new Intent(view.getContext(), TaskActivity.class);
                    intent.putExtra("task_id", position);
                    intent.putExtra("task", items[position]);
                    
                    startActivity(intent);
                }
            });
        }
        
    }
}