package com.example.job;

import static androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_job_Info_Activity extends AppCompatActivity
{

    private  final  static int gallerypic=1;
    private DatabaseReference databaseReference,SellersRef;
    private StorageReference storageReference;
    private  String DownloadImageUri;
    private Uri ImageUri;
    private String RandomKey;
    CircleImageView OrgImg;
    private String SaveCurrentDate,SaveCurrentTime;
    EditText  CompanyName,JobTittle,JobSkills,JobDescription,JobSalary;
    Button UploadInfo;
    String jobTittle,companyName,Skills,jobDescription,salary;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_info);



        storageReference= FirebaseStorage.getInstance().getReference().child("Company Logo");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Job Information");


        OrgImg=(CircleImageView) findViewById(R.id.profile_image_details) ;
        JobTittle=(EditText)findViewById(R.id.Job_Tittle) ;
        CompanyName=(EditText)findViewById(R.id.company_name) ;
        JobSkills=(EditText)findViewById(R.id.SkillsandRequirements) ;
        JobDescription=(EditText)findViewById(R.id.Job_Descrition) ;
        JobSalary=(EditText) findViewById(R.id.Salary) ;
        UploadInfo=(Button)findViewById(R.id.button1);


         OrgImg.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                 Intent intent= new Intent();
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 intent.setType("image/*");
                 startActivityForResult(intent,gallerypic);

             }
         });


         UploadInfo.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                 ValidateInfo();
                 SaveInfoToDatabase();
             }

         });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)

    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypic && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            OrgImg.setImageURI(ImageUri);

        }
    }




    private void ValidateInfo()
    {
        jobTittle=JobTittle.getText().toString();
        companyName=CompanyName.getText().toString();
        jobDescription=JobDescription.getText().toString();
        Skills=JobSkills.getText().toString();
        salary=JobSalary.getText().toString();



        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Company Logo", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty( jobTittle))
        {
            Toast.makeText(getApplicationContext(), "Please Set  Job Tittle", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(companyName))
        {
            Toast.makeText(getApplicationContext(), "Please Set company Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(jobDescription))
        {
            Toast.makeText(getApplicationContext(), "Please Set Job Description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Skills))
        {
            Toast.makeText(getApplicationContext(), "Please Set Skills and Requirements ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(salary))
        {
            Toast.makeText(getApplicationContext(), "Please Set Job Salary", Toast.LENGTH_SHORT).show();
        }
        else {
            SavePhotoToStorage();
        }

    }







    private void SavePhotoToStorage()
    {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calendar.getTime());

        RandomKey=SaveCurrentDate+SaveCurrentTime;


        final StorageReference filePath = storageReference.child(ImageUri.getLastPathSegment() + RandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(getApplicationContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(), ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }


                        DownloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            DownloadImageUri = task.getResult().toString();

                            Toast.makeText(getApplicationContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveInfoToDatabase();
                        }
                    }
                });
            }
        });

    }








    private void SaveInfoToDatabase()
    {

        HashMap<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("careid", RandomKey);
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("image", DownloadImageUri);
        phoneMap.put("jobtittle",jobTittle);
        phoneMap.put("companyname",companyName);
        phoneMap.put("skills",Skills );
        phoneMap.put("jobdescription",jobDescription );
        phoneMap.put("jobsalary", salary);

        databaseReference
                .child(RandomKey)
                .updateChildren(phoneMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), Add_job_Info_Activity.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Car Info Is Added Successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }








}