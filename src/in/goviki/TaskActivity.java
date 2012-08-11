package in.goviki;

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
        Integer task_id = getIntent().getExtras().getInt("task_id");
        Task[] items = HackyStuff.getTaskArray();
        Task thisTask = items[task_id];

        this.setTitle(thisTask.name);

        // Populate UI from the task details
        _officeTextView.setText(thisTask.office);
        _addressTextView.setText(thisTask.address);
        _procedureEditText.setText(thisTask.procedure);
        
        _toggleEditProcedure.setOnClickListener(new ToggleEditProcedureClickHandler() );
    }
    
    
    public class ToggleEditProcedureClickHandler implements View.OnClickListener 
    {
        public void onClick( View view ) {
            
            if (_toggleEditProcedure.isChecked() ) {
                _procedureEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                _procedureEditText.setInputType(InputType.TYPE_NULL);
            }
        }
    }
    
    
    

}
