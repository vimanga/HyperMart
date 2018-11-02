package afinal.hp.madfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import afinal.hp.madfinal.database.DBHelper;

public class Register extends AppCompatActivity {

    EditText userName,password,birthDate;
    String UserName,Password,BirthDate,Gender;
    RadioGroup gender;
    RadioButton genderRadio;
    Button reg;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        userName = (EditText) findViewById(R.id.name);
        birthDate = (EditText) findViewById(R.id.birth);
        password = (EditText) findViewById(R.id.pass);
        gender = (RadioGroup)findViewById(R.id.radioGroup);
        reg = (Button) findViewById(R.id.buttonSubmit);


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = gender.getCheckedRadioButtonId();


                if(id == R.id.radioMale){

                    genderRadio = findViewById(R.id.radioMale);

                }
                else if(id == R.id.radioFemale){

                    genderRadio = findViewById(R.id.radioFemale);
                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User u = new User();

                UserName = userName.getText().toString().trim();
                BirthDate = birthDate.getText().toString().trim();
                Password = password.getText().toString().trim();
                Gender   = genderRadio.getText().toString().trim();

                u.setUserName(UserName);
                u.setDateOfBirth(BirthDate);
                u.setPassword(Password);
                u.setGender(Gender);

                boolean result = db.addInfo(u);

                if(result){

                    Toast.makeText(Register.this,"User Registered",Toast.LENGTH_SHORT).show();
                    Register.this.startActivity(new Intent(Register.this,Home.class));

                }

                else {

                    Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });








    }
}
