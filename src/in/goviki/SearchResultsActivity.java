package in.goviki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

public class SearchResultsActivity extends Activity {
    
    ListView listView1;
    ArrayAdapter<Task> adapter;
    Gson gson = new Gson();
    
    Task[] items;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        
        // Get the task from the intent
        String json = getIntent().getExtras().getString("results");

        listView1 = (ListView) findViewById(R.id.listView1);

        // Bind the list view to an empty adapter
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1);
        // Add entries to list
        items = gson.fromJson(json, Task[].class);
        for (int i=0; i< items.length; i++) {
            Task item = items[i];
            adapter.add(item);
        }
        
        listView1.setAdapter(adapter);
    
        listView1.setOnItemClickListener(new OnItemClickListener() {
            // Click handler for each list item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                
                // Go into task details page
                Intent intent = new Intent(view.getContext(), TaskActivity.class);
                Task task = items[position];
                intent.putExtra("task_id", task.id);
                intent.putExtra("task", task);
                
                startActivity(intent);
            }
        });
    }

}
