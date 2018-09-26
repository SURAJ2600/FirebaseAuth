package androidmasterminds.com.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.phone)
    EditText mPhone_edt;


    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        btn_login.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {


        // So we will make
        switch (view.getId() /*to get clicked view id**/) {
            case R.id.btn_login:

                startActivity(new Intent(MainActivity.this,VerificationActivity.class).putExtra("mobile",mPhone_edt.getText().toString().trim()));



                break;

            default:
                break;
        }

    }




}
