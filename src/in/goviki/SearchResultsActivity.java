package in.goviki;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class SearchResultsActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        
        // Get the task from the intent
        String results = getIntent().getExtras().getString("results");
        Toast.makeText(this, results, Toast.LENGTH_LONG).show();
    }

}
