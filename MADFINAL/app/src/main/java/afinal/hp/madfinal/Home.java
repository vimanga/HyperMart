package afinal.hp.madfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import afinal.hp.madfinal.database.DBHelper;

public class Home extends AppCompatActivity {

    Button register,login;
    DBHelper db;
    EditText userName,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DBHelper(this);

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

        userName = (EditText) findViewById(R.id.editTextUserName);
        password = (EditText) findViewById(R.id.editTextPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();

                String newUserName = userName.getText().toString();
                String newPassword = password.getText().toString();

                user.setUserName(newUserName);
                user.setPassword(newPassword);

                Boolean result = db.checkValidity(user);

                if(result){

                    Toast.makeText(Home.this,"Valid Credentials",Toast.LENGTH_SHORT).show();

                    int id = db.getId(newUserName);

                    SharedPreferences sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putInt("UserName",id);
                    ed.commit();

                    Home.this.startActivity(new Intent(Home.this,ProfileManagement.class));

                }
                else {

                    Toast.makeText(Home.this,"Invalid Credentials,Try again",Toast.LENGTH_SHORT).show();
                }


            }
        });





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Home.this,"Loading Registration Page",Toast.LENGTH_SHORT);
                Home.this.startActivity(new Intent(Home.this,Register.class));
            }
        });


    }
}
