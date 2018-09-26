package androidmasterminds.com.firebaseauth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidmasterminds.com.firebaseauth.Utilities.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationActivity extends AppCompatActivity {

    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;


    //firebase auth object
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private Context mContext;

    @BindView(R.id.otp1_txt)
    TextView otp1_txt;

    @BindView(R.id.otp2_txt)
    TextView otp2_txt;

    @BindView(R.id.otp3_txt)
    TextView otp3_txt;

    @BindView(R.id.otp4_txt)
    TextView otp4_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ButterKnife.bind(this);


        mContext = VerificationActivity.this;

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        getIntentData();
    }

    private void getIntentData() {


        if (getIntent().hasExtra("mobile")) {

            if (Util.isNetworkConnected(mContext)) {
                String mobile = getIntent().getStringExtra("mobile").toString().trim();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + "" + mobile,
                        60,
                        TimeUnit.SECONDS,
                        TaskExecutors.MAIN_THREAD,
                        mCallbacks);
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                String codes = code;
                Log.d("codes", ">>>>>>>>>>>>>>" + codes);


                try {


                    otp1_txt.setText("" + codes.charAt(0));
                    otp2_txt.setText("" + codes.charAt(1));
                    otp3_txt.setText("" + codes.charAt(2));
                    otp4_txt.setText("" + codes.charAt(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                verifyVerificationCode(code);

                //verifying the code
                // mobile(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationActivity
                    .this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String code) {


        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                        /*    Intent intent = new Intent(VerifyPhoneActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/

                            Intent intent = new Intent(VerificationActivity.this, GeneralListingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Log.d("success", ">>>");

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parents), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }
}
