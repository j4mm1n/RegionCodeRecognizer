package projectrcr.regioncoderecognizer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.Stack;

import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText textView;
    public Stack<Integer> List_Display = new Stack<>();
    public String StackNumbers = "";
    public String CountryCode = "";
    public CountryCodes CC = new CountryCodes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising display.
        textView = (EditText) findViewById(R.id.editText);

        //Initialising all buttons.
        Button button_0 = (Button) findViewById(R.id.button_0);
        Button button_1 = (Button) findViewById(R.id.button_1);
        Button button_2 = (Button) findViewById(R.id.button_2);
        Button button_3 = (Button) findViewById(R.id.button_3);
        Button button_4 = (Button) findViewById(R.id.button_4);
        Button button_5 = (Button) findViewById(R.id.button_5);
        Button button_6 = (Button) findViewById(R.id.button_6);
        Button button_7 = (Button) findViewById(R.id.button_7);
        Button button_8 = (Button) findViewById(R.id.button_8);
        Button button_9 = (Button) findViewById(R.id.button_9);
        Button button_clear = (Button) findViewById(R.id.button_clear);
        Button button_checkNumber = (Button) findViewById(R.id.button_checkNumber);
        ImageButton button_call = (ImageButton) findViewById(R.id.button_call);

        //Inserting buttons in Array to use in loop for assigning OnClickListeners.
        Button buttonNumberList[] = {button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9};

        //OnClickListener for dial Number button.
        button_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialActivity();
            }
        });

        //OnClickListener for delete button.
        button_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!List_Display.isEmpty()) {
                    List_Display.pop();
                    textView.setText(getStackNumbers());
                }
            }
        });

        //OnClickListener for check Number button.
        button_checkNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getCountryCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Loop to make OnClickListener for all of the numbers buttons.
        for (final Button button : buttonNumberList) {
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (List_Display.size() <= 15) {
                        List_Display.add(Integer.parseInt(button.getText().toString()));
                        textView.setText(getStackNumbers());
                    }
                }
            });
        }
    }

    //Sending entered numbers to Dialer to start call.
    private void startDialActivity(){
        if(isTelephonyEnabled()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + getStackNumbers()));
            startActivity(intent);
        }
    }

    //To ensure that device is compatible with making a phonecall.
    private boolean isTelephonyEnabled(){
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState()==TelephonyManager.SIM_STATE_READY;
    }

    //Method to get all the numbers from Stack.
    private String getStackNumbers() {
        StackNumbers = "";
        for (int i = 0; i < List_Display.size(); i++) {
            StackNumbers += List_Display.get(i).toString();
        }
        return StackNumbers;
    }

    //Method to get country code from entered numbers and check if it's valid.
    private String getCountryCode() {
        if (!List_Display.isEmpty() && !(List_Display.size() < 4)) {
            CountryCode = "";
            for (int i = 0; i < (List_Display.size()-10); i++) {
                CountryCode += List_Display.get(i).toString();
            }
            return CC.checkCountryCode(CountryCode);
        }
        else if(List_Display.size() < 13){
            return "No valid phone number entered!";
        }
        return "No Country Code found.";
    }
}


