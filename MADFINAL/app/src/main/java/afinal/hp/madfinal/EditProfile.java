package afinal.hp.madfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import afinal.hp.madfinal.database.DBHelper;

public class EditProfile extends AppCompatActivity {

    EditText userName, password, dateOfBirth;
    DBHelper db;
    RadioGroup gender;
    RadioButton male, female;
    Button edit,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        final int user = sp.getInt("UserName", 0);


        db = new DBHelper(this);


        userName = (EditText) findViewById(R.id.editUserName);
        password = (EditText) findViewById(R.id.editPassword);
        dateOfBirth = (EditText) findViewById(R.id.editDateOfBirth);
        gender = (RadioGroup) findViewById(R.id.editRadioGroup);
        male = (RadioButton) findViewById(R.id.editRadioMale);
        female = (RadioButton) findViewById(R.id.editRadioFemale);

        edit = (Button) findViewById(R.id.buttonEdit);
        delete = (Button) findViewById(R.id.buttonDelete);

        Cursor result = db.readAllInfor(user);

        if (result.getCount() == 0) {

            Toast.makeText(EditProfile.this, "No data to display", Toast.LENGTH_SHORT).show();
            return;
        } else {

            while (result.moveToNext()) {

                String newUserName = result.getString(1);
                String newPassword = result.getString(4);
                String date = result.getString(2);
                String newGender = result.getString(3);

                Log.d("TEST", newUserName);


                userName.setText(newUserName);
                password.setText(newPassword);
                dateOfBirth.setText(date);

                if (newGender.equals("Male")) {

                    male.setChecked(true);

                } else {

                    female.setChecked(true);
                }


            }


        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newUserName =  userName.getText().toString().trim();
                String newPassword =  password.getText().toString().trim();
                String date = dateOfBirth.getText().toString().trim();
                String newGender;

                int id = gender.getCheckedRadioButtonId();

                if(id == R.id.editRadioFemale){

                    newGender = female.getText().toString().trim();
                }

                else {

                    newGender = male.getText().toString().trim();

                }


                User u = new User();

                u.setUserName(newUserName);
                u.setPassword(newPassword);
                u.setDateOfBirth(date);
                u.setGender(newGender);

                Boolean result = db.updateInfor(u,user);

                if(result){

                    Toast.makeText(EditProfile.this,"Update Successful",Toast.LENGTH_SHORT).show();
                    EditProfile.this.startActivity(new Intent(EditProfile.this,ProfileManagement.class));

                }

                else {
                    Toast.makeText(EditProfile.this,"Update Failed",Toast.LENGTH_SHORT).show();
                    return;


                }


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int result = db.deleteInfo(user);

                if(result > 0){

                    Toast.makeText(EditProfile.this,"User Deleted",Toast.LENGTH_SHORT).show();
                    EditProfile.this.startActivity(new Intent(EditProfile.this,Home.class));

                }
                else{
                    Toast.makeText(EditProfile.this,"User removing ended with an error",Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

    }
}