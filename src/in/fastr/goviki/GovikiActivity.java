package in.fastr.goviki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GovikiActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        
        Task[] items = HackyStuff.getTaskArray();
        
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this,
                    android.R.layout.simple_list_item_1, items);
        
        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new OnItemClickListener() {
         
            // Click handler for each list item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                
                // Go into task details page
                Intent intent = new Intent(view.getContext(), TaskActivity.class);
                intent.putExtra("task_id", position);
                
                startActivity(intent);
            }
        });

    }
}