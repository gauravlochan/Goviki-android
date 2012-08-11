package in.goviki;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TaskActivity extends Activity {

    protected TextView _officeTextView;
    protected TextView _addressTextView;
    protected EditText _procedureEditText;
    protected ToggleButton _toggleEditProcedure;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);
        
        _officeTextView = (TextView) findViewById( R.id.officeTextView );
        _addressTextView = (TextView) findViewById( R.id.addressTextView );
        _procedureEditText = (EditText) findViewById( R.id.procEditText );
        _toggleEditProcedure = (ToggleButton) findViewById( R.id.toggleButton1 );
        
        // Get the task from the intent
        Task task = (Task) getIntent().getExtras().getSerializable("task");
        Task thisTask = task; 

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
                _procedureEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                // Submit!
                
                _procedureEditText.setInputType(InputType.TYPE_NULL);
            }
        }
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
