package afinal.hp.madfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import afinal.hp.madfinal.database.DBHelper;

public class ProfileManagement extends AppCompatActivity {

    Button update;
    EditText userName,password,dateOfBirth;
    RadioGroup gender;
    RadioButton male,female;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        SharedPreferences sp = this.getSharedPreferences("Login",MODE_PRIVATE);
            int user = sp.getInt("UserName" ,0);


            db = new DBHelper(this);


            userName = (EditText) findViewById(R.id.viewUserName);
            password = (EditText)findViewById(R.id.viewPassword);
            dateOfBirth = (EditText)findViewById(R.id.viewDateOfBirth);
            gender = (RadioGroup) findViewById(R.id.viewRadioGroup);
            male = (RadioButton) findViewById(R.id.viewRadioMale);
            female = (RadioButton) findViewById(R.id.viewRadioFemale);
            update = (Button) findViewById(R.id.buttonUpdate);

            Cursor result = db.readAllInfor(user);

            if(result.getCount() == 0){

                Toast.makeText(ProfileManagement.this,"No data to display",Toast.LENGTH_SHORT).show();
                return;
            }

            else {

                while (result.moveToNext()){

                    String newUserName = result.getString(1);
                    String newPassword = result.getString(4);
                    String date        = result.getString(2);
                    String newGender       = result.getString(3);

                    Log.d("TEST",newUserName);


                    userName.setText(newUserName);
                    password.setText(newPassword);
                    dateOfBirth.setText(date);

                    userName.setEnabled(false);
                    password.setEnabled(false);
                    dateOfBirth.setEnabled(false);

                    if(newGender.equals("Male")){

                        male.setChecked(true);
                        male.setEnabled(false);
                        female.setEnabled(false);

                    }

                    else {

                        female.setChecked(true);
                        female.setEnabled(false);
                        male.setEnabled(false);
                    }



                }


            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ProfileManagement.this.startActivity(new Intent(ProfileManagement.this,EditProfile.class));


                }
            });



    }



}
