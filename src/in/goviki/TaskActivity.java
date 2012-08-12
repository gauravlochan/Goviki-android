package in.goviki;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TaskActivity extends Activity {

    protected TextView _officeTextView;
    protected TextView _addressTextView;
    protected EditText _procedureEditText;
    protected ToggleButton _toggleEditProcedure;
    
    Task thisTask;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);
        
        _officeTextView = (TextView) findViewById( R.id.officeTextView );
        _addressTextView = (TextView) findViewById( R.id.addressTextView );
        _procedureEditText = (EditText) findViewById( R.id.procEditText );
        _toggleEditProcedure = (ToggleButton) findViewById( R.id.toggleButton1 );
        
        // Get the task from the intent
        thisTask = (Task) getIntent().getExtras().getSerializable("task");

        this.setTitle(thisTask.title);

        // Populate UI from the task details
        _officeTextView.setText( thisTask.getOffice() );
        _addressTextView.setText( thisTask.getAddress() );
        _procedureEditText.setText(thisTask.body);
        
        _toggleEditProcedure.setOnClickListener(new ToggleEditProcedureClickHandler() );
    }

    
    
    public class ToggleEditProcedureClickHandler implements View.OnClickListener 
    {
        public void onClick( View view ) {
            
            if (_toggleEditProcedure.isChecked() ) {
                _procedureEditText.setEnabled(true);
//                _procedureEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            } else {
                // Submit!
                thisTask.body = _procedureEditText.getText().toString();
                new UpdateTaskAsyncTask().execute();
                _procedureEditText.setEnabled(false);
                
//                _procedureEditText.setInputType(InputType.TYPE_NULL);
            }
        }
    }

    public String updateTask(int id, String body, String title) {
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        nameValuePairs.add(new BasicNameValuePair("[task][body]", body));
        nameValuePairs.add(new BasicNameValuePair("task[title]", title));
        
        String tasksUrl = "http://goviki.herokuapp.com/tasks/"+id;
        String response = RESTHelper.putData(tasksUrl, nameValuePairs);
        
        return response;
    }

    
    public class UpdateTaskAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(
                    TaskActivity.this,
                    "Please wait...", // title
                    "Updating task", // message
                    true // indeterminate
                    );
        }

        @Override
        protected String doInBackground(Void... params) {
            String response = updateTask(thisTask.id, thisTask.body, thisTask.title);
            return response;
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

            // do nothing else
        }
    }

    

}
