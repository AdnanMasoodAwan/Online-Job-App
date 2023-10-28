package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

/** @noinspection deprecation*/
public class Sign_In_Activity extends AppCompatActivity
{
    private ProgressDialog mLoadingBar;
    Button SignIn;
    EditText Email,Password;
    FirebaseAuth Auth,Auth1;
    String currentUserId;
    int CheckRadioButtonId;
    String CheckRadioButton;
    DatabaseReference databaseReference;

    TextView donthaveaccount;

    String Type="Admin";
    String Type1="User";

    RadioGroup radioGroup;
    RadioButton Admin,User;
    CheckBox chkBoxRememberMe;

    DatabaseReference Ad_Ref;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Auth=FirebaseAuth.getInstance();
        Auth1=FirebaseAuth.getInstance();
      //  currentUserId=Auth.getCurrentUser().getUid();
        Ad_Ref=FirebaseDatabase.getInstance().getReference().child("Admins");


        donthaveaccount=(TextView) findViewById(R.id.Dont_ave_Account);
        Password=(EditText)findViewById(R.id.Password_Sign_In);
        Email=(EditText)findViewById(R.id.Email_Sign_In);
        SignIn=(Button)findViewById(R.id.Sign_In_button);
        radioGroup=(RadioGroup)findViewById(R.id.SignInRadioGroup);
        Admin=(RadioButton)findViewById(R.id.SignInAdmin);
        User=(RadioButton)findViewById(R.id.SignInUser);
        mLoadingBar=new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        donthaveaccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Sign_Up_Activity.class);
                startActivity(intent);
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CheckRadioButtonId=radioGroup.getCheckedRadioButtonId();
                switch (CheckRadioButtonId)
                {
                    case  R.id.SignInAdmin:

                         AdminValidateInfo();

                        break;

                    case R.id.SignInUser:

                        UserValidateInfo();

                        break;
                }


            }
        });






    }



    private void AdminValidateInfo()
    {

        String phone=Email.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast. makeText(getApplicationContext(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {

            mLoadingBar.setTitle("Signing_In");
            mLoadingBar.setMessage("Please Wait while we are Signing You In!");
            mLoadingBar.setIcon(R.drawable.plus);
            mLoadingBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();




            if(chkBoxRememberMe.isChecked())
            {
                Paper.book().write(Prevelant.UserPhoneKey,phone);
                Paper.book().write(Prevelant.UserPasswordKey,password);
                Paper.book().write(Prevelant.UserTypeKey,"Admin");
            }

            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                    if((snapshot.child("Users").child(phone).exists()))
                    {
                        Users users=snapshot.child("Users").child(phone).getValue(Users.class);

                        if(users.getPhone().equals(phone))
                        {
                            if(users.getPassword().equals(password))
                            {
                                if(users.getType().equals("Admin"))
                                {
                                    Toast.makeText(getApplicationContext(), " Admin You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Admin_panel_Activity.class);
                                    startActivity(intent);
                                    mLoadingBar.dismiss();
                                }

                            }

                        }


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This User "+phone+" not Exsits", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });




        }



    }





    private void UserValidateInfo()
    {

        String phone=Email.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {
            mLoadingBar.setTitle("Signing_In");
            mLoadingBar.setMessage("Please Wait while we are Signing You In!");
            mLoadingBar.setIcon(R.drawable.plus);
            mLoadingBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();


            if(chkBoxRememberMe.isChecked())
            {
                Paper.book().write(Prevelant.UserPhoneKey,phone);
                Paper.book().write(Prevelant.UserPasswordKey,password);
                Paper.book().write(Prevelant.UserTypeKey,"User");
            }


            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if((snapshot.child("Users").child(phone).exists()))
                    {
                        Users users=snapshot.child("Users").child(phone).getValue(Users.class);

                        if(users.getPhone().equals(phone))
                        {
                            if(users.getPassword().equals(password))
                            {
                                if(users.getType().equals("User"))
                                {

                                    Toast.makeText(getApplicationContext(), "User! You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),Find_Jobs_Activity.class);
                                    startActivity(intent);
                                    mLoadingBar.dismiss();

                                }


                            }
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This User "+phone+" not Exsits", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });






        }



    }















    @Override
    protected void onStart()
    {


        super.onStart();



       // FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        // currentUserId=Auth.getCurrentUser().getUid();


        CheckRadioButtonId=radioGroup.getCheckedRadioButtonId();
        switch (CheckRadioButtonId)
        {
            case  R.id.SignInAdmin :



                Intent intent=new Intent(getApplicationContext(), Admin_panel_Activity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);




                break;

            case R.id.SignInUser:



                Intent intent1=new Intent(getApplicationContext(),Find_Jobs_Activity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);


                break;
        }

    }








}