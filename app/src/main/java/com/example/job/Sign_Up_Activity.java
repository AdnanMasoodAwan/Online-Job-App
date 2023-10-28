package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/** @noinspection deprecation*/
public class Sign_Up_Activity extends AppCompatActivity
{
    private ProgressDialog mLoadingBar;
    Button SignUp,PhoneSignUp;
    TextView AlreadyHaveAccount;
    EditText Name,Email,Password,CNIC,Phone;
    FirebaseAuth Auth;
    String currentUserId,type;
    int CheckRadioButtonId;
    CircleImageView ProfileImage;
    private DatabaseReference AdminsRef,UsersRef;
    private DatabaseReference AdminRef;
    String Type="Admin";
    String Type1="User";


    String SaveCurrentDate,SaveCurrentTime,RandomPostNam;


    RadioGroup radioGroup;
    RadioButton Admin,User;


    CircleImageView Image;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Auth = FirebaseAuth.getInstance();
//        currentUserId=Auth.getCurrentUser().getUid();
        //AdminsRef = FirebaseDatabase.getInstance().getReference().child("Admins");
       // AdminRef = FirebaseDatabase.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("Admin Profile Images");


        Name = (EditText) findViewById(R.id.User_Name_Sign_Up);
        Password = (EditText) findViewById(R.id.Password_Sign_Up);
        Phone = (EditText) findViewById(R.id.Phone_Sign_Up);
        Email = (EditText) findViewById(R.id.Email_Sign_Up);
        SignUp = (Button) findViewById(R.id.Sign_Up_button);
        AlreadyHaveAccount = (TextView) findViewById(R.id.Already_ave_Account);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Admin = (RadioButton) findViewById(R.id.Admin);
        User = (RadioButton) findViewById(R.id.User);
        ProfileImage = (CircleImageView) findViewById(R.id.profile_image_Sign_Up);
        mLoadingBar=new ProgressDialog(this);





        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 Intent intent=new Intent(getApplicationContext(),Sign_In_Activity.class);
                 startActivity(intent);
            }
        });



        SignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                CheckRadioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (CheckRadioButtonId)
                {
                    case R.id.Admin:

                        ValidateInfoForAdmin();

                              /*
                         AdminsRef.addValueEventListener(new ValueEventListener()
                         {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot)
                             {

                                 if (snapshot.exists())
                                 {



                                     String name1 = Name.getText().toString();
                                     String email1 = Email.getText().toString();//
                                     // String password1 = Password.getText().toString();
                                     String cnic1 = CNIC.getText().toString();

                                     String nam = String.valueOf(snapshot.child(currentUserId).child("name").getValue());
                                     String emai = String.valueOf(snapshot.child(currentUserId).child("email").getValue());
                                     String cni = String.valueOf(snapshot.child(currentUserId).child("cnic").getValue());
                                     String typ = String.valueOf(snapshot.child(currentUserId).child("type").getValue());
                                     //String Pic = snapshot.child("profileimage").getValue().toString();


                                     Toast.makeText(getApplicationContext(), "" + currentUserId, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + nam, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + emai, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + cni, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + typ, Toast.LENGTH_SHORT).show();




                                     if (name1.equals(nam)   && cnic1.equals(cni) && typ.equals("Admin"))
                                     {
                                         Toast.makeText(getApplicationContext(), "You are Admin", Toast.LENGTH_SHORT).show();
                                         ValidateInfoForAdmin();
                                         ValidateInfo();

                                     }
                                     else {

                                         Toast.makeText(getApplicationContext(), "Sorry you are not autohrized to Use as Admin ", Toast.LENGTH_SHORT).show();

                                     }


                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }

                         } ); */







                        break;

                    case R.id.User:

                        ValidateInfoForUser();
                       // SavePhotoToStorageForUser();
                        break;
                }


            }

        });

    }









    private void ValidateInfoForUser()
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String phone=Phone.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter phone", Toast.LENGTH_SHORT).show();
        }



        else
        {

            mLoadingBar.setTitle("Signing_Up");
            mLoadingBar.setMessage("Please Wait while we are Registering New  User!");
            mLoadingBar.setIcon(R.drawable.plus);
            mLoadingBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();




            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                     if(!(snapshot.child("Users").child(phone).exists()))
                    {


                        HashMap<String,Object> Adminsmap=new HashMap<>();
                        Adminsmap.put("currentuserid",phone);
                        Adminsmap.put("name",name);
                        Adminsmap.put("email",email);
                        Adminsmap.put("phone",phone);
                        Adminsmap.put("password",password);
                        Adminsmap.put("type",Type1);

                        databaseReference.child("Users").child(phone).setValue(Adminsmap)
                                .addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "User Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();
                                            mLoadingBar.dismiss();
                                            Intent intent=new Intent(getApplicationContext(),Find_Jobs_Activity.class);
                                            startActivity(intent);

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Sorry you are not Authorized", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });




                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "This User "+Email+" already  Exsits", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });



        }


    }




    private void ValidateInfoForAdmin()
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String phone=Phone.getText().toString();
        String password=Password.getText().toString();



        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter phone", Toast.LENGTH_SHORT).show();
        }


        else
        {

            mLoadingBar.setTitle("Signing_Up Admin");
            mLoadingBar.setMessage("Please Wait while we are Registering Admin!");
            mLoadingBar.setIcon(R.drawable.plus);
            mLoadingBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();




            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                    if((snapshot.child("Users").child(phone).exists()))
                   {
                        Users users=snapshot.child("Users").child(phone).getValue(Users.class);

                        if((     (users.getPassword().equals(password))
                                &&     (users.getEmail().equals(email))
                                &&  (users.getName().equals(name)) ) )
                        {



                            HashMap<String,Object> Adminsmap=new HashMap<>();
                            Adminsmap.put("currentuserid",phone);
                            Adminsmap.put("name",name);
                            Adminsmap.put("email",email);
                            Adminsmap.put("phone",phone);
                            Adminsmap.put("password",password);
                            Adminsmap.put("type",Type);

                            databaseReference.child("Users").child(phone).setValue(Adminsmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(), "Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();
                                                mLoadingBar.dismiss();
                                                Intent intent=new Intent(getApplicationContext(),Admin_panel_Activity.class);
                                                startActivity(intent);

                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Sorry you are not Authorized", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    });


                        }

                    }


                    else
                    {
                     Toast.makeText(getApplicationContext(), "sorry ! This User "+name+" not  Exsits you are not Admin ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });



        }


    }















}