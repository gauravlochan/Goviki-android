package in.goviki;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

public class GovikiActivity extends Activity {
    ListView listView1;
    SearchView searchView;
    
    String allTasksUrl = "http://goviki.herokuapp.com/tasks.json";
    String searchTasksUrl = "http://goviki.herokuapp.com/tasks.json?q=";
    
    ArrayAdapter<Task> adapter;
    Task[] items = {};
    Gson gson = new Gson();
    
    boolean showSearch = false;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        searchView = (SearchView) findViewById(R.id.searchView1);
        listView1 = (ListView) findViewById(R.id.listView1);

        // Bind the list view to an empty adapter
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1);
        listView1.setAdapter(adapter);

        // Enable search requests
        searchView.setOnQueryTextListener(new SearchListener());
        
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

        new GetTasksAsyncTask().execute();
    }
    
    // Handles search requests in the SearchView
    public class SearchListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            new SearchAsyncTask().execute(new String[] { query });
            return true;
        }
    }

    
    // Once search is kicked off, asynchronously gets results
    public class SearchAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(
                    GovikiActivity.this,
                    "Please wait...", // title
                    "Searching for matching tasks", // message
                    true // indeterminate
                    );
        }

        @Override
        protected String doInBackground(String... params) {
            String url = searchTasksUrl + params[0];
            String json = RESTHelper.simpleGet(url);
            return json;
        }
        
        
        @Override
        protected void onPostExecute(String searchResult) {
            // Temporary fix for the "View not attached to window manager" issue
            try {
                this.progressDialog.cancel();
            } catch (IllegalArgumentException e) {
                // The original activity has been killed, don't crash the app
                return;
            }
            
            // If nothing was found, stay on the activity and let user try again
            if ((searchResult==null) || (searchResult.length()==0)) {
                Toast.makeText(GovikiActivity.this, "No results found, try again", Toast.LENGTH_LONG).show();
                return;
            }
            
            // Go into task details page
            Intent intent = new Intent(GovikiActivity.this, SearchResultsActivity.class);
            intent.putExtra("results", searchResult);
            startActivity(intent);
        }
    }

    
    // This is kicked off when the page loads, to try and find all the 
    // tasks from the server
    public class GetTasksAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            adapter.clear(); // clear "old" entries (optional)
        }

        @Override
        protected String doInBackground(Void... params) {
            String json = RESTHelper.simpleGet(allTasksUrl);
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

        }
        
    }
}