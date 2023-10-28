package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.LoadState;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Job_Details_Activity extends AppCompatActivity {
    private DatabaseReference ProductsRef, ApplyReference,SaveReference;

    CircleImageView circleImageView;
    TextView Jobtittle, company, postedTime, salary, jobDescription, Skills;
    String Tittle, Company, PostedTime, Salary, JobDescription, skills, PostKey,Status,PostedDate;
    Button Apply, Save;

    String SaveCurrentDate,SaveCurrentTime,RandomKey;
    //ProgressBar
     ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        circleImageView = (CircleImageView) findViewById(R.id.Friend_profile_image);
        Jobtittle = (TextView) findViewById(R.id.Friend_user_name);
        company = (TextView) findViewById(R.id.Firend_user_Date);
        postedTime = (TextView) findViewById(R.id.dislike_post);
        salary = (TextView) findViewById(R.id.Salary);
        jobDescription = (TextView) findViewById(R.id.Firend_user_Dat);
        Skills = (TextView) findViewById(R.id.Firend_user_Da);
        Apply = (Button) findViewById(R.id.Apply);
        Save = (Button) findViewById(R.id.Save);

        PostKey = (String) getIntent().getExtras().get("postkey");
        Tittle = (String) getIntent().getExtras().get("jobtittle");
        Company = (String) getIntent().getExtras().get("company");
        PostedTime = (String) getIntent().getExtras().get("time");
        PostedDate = (String) getIntent().getExtras().get("date");
        Salary = (String) getIntent().getExtras().get("salary");
        // image=(String) getIntent().getExtras().get("image");
        JobDescription = (String) getIntent().getExtras().get("description");
        skills = (String) getIntent().getExtras().get("skills");


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Job Information").child(PostKey);
        ApplyReference = FirebaseDatabase.getInstance().getReference().child("Applied Jobs").child(PostKey);
        SaveReference = FirebaseDatabase.getInstance().getReference().child("Saved Jobs").child(PostKey);

        Jobtittle.setText(Tittle);
        company.setText(Company);
        postedTime.setText(PostedTime);
        salary.setText(Salary);
        jobDescription.setText(JobDescription);
        Skills.setText(skills);
        setProductData();


        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                     ApplyForJob();
            }
        });


        Save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                  SavedJobs();
            }
        });


    }



    private void ApplyForJob()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calendar.getTime());

        RandomKey=SaveCurrentDate+SaveCurrentTime;





        HashMap<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("careid", PostKey);
        phoneMap.put("applytime", SaveCurrentTime);
        phoneMap.put("applydate", SaveCurrentDate);
        phoneMap.put("jobtittle", Tittle);
        phoneMap.put("companyname", Company);
        phoneMap.put("skills",skills);
        phoneMap.put("jobdescription", JobDescription);
        phoneMap.put("jobsalary", Salary);
        phoneMap.put("status","applied");



        ApplyReference.setValue(phoneMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(getApplicationContext(), "You Applied For Job..", Toast.LENGTH_SHORT).show();

            }
        });


    }





    private void SavedJobs()
    {
       /* Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calendar.getTime());

        RandomKey=SaveCurrentDate+SaveCurrentTime;


        */

        HashMap<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("careid", PostKey);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("jobtittle", Tittle);
        phoneMap.put("companyname", Company);
        phoneMap.put("skills",skills);
        phoneMap.put("jobdescription", JobDescription);
        phoneMap.put("jobsalary", Salary);
        phoneMap.put("status","saved");



        SaveReference.setValue(phoneMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(getApplicationContext(), "You Saved this Job..", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),SavedJobsActivity.class);
                startActivity(intent);

            }
        });

    }



    private void setProductData() {
        ProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String Image = snapshot.child("image").getValue().toString();
                    Picasso.get().load(Image).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }











}