package com.example.job;

import static com.example.job.R.id.Saved_Jobs_List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedJobsActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    CircleImageView circleImageView,ProfilePhoto,SearcIcon;
    TextView User;
    EditText UserInput;

    DatabaseReference databaseReference;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

        recyclerView=(RecyclerView) findViewById(R.id.Saved_Jobs_List);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Saved Jobs");
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SavedJobsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



    }



    @Override
    protected void onStart()
    {

        super.onStart();

        //Query query = databaseReference.orderByChild("jobtittle").startAt(String.valueOf(UserInput)).endAt(String.valueOf(UserInput) + "\uf8ff");

        FirebaseRecyclerOptions<Applied_Job_Posts> options=
                new FirebaseRecyclerOptions.Builder<Applied_Job_Posts>()
                        .setQuery(databaseReference,Applied_Job_Posts.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Applied_Job_Posts, SavedJobsPostsViewHolder>(options)
        {

            public SavedJobsPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.applied_jobs_layout, parent, false);

                return new SavedJobsPostsViewHolder(view);
            }


            protected void onBindViewHolder(@NonNull SavedJobsPostsViewHolder holder, int position, Applied_Job_Posts model)
            {

                String PostKey = getRef(position).getKey();

                holder.setJobtittle(model.getJobtittle());
                holder.setCompanyname(model.getCompanyname());
                holder.setApplydate(model.getApplydate());
                holder.setApplytime(model.getApplytime());


                Toast.makeText(getApplicationContext(),model.getJobtittle(), Toast.LENGTH_SHORT).show();

                holder.view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent=new Intent(getApplicationContext(),Job_Details_Activity.class);
                        intent.putExtra("postkey",PostKey);
                        intent.putExtra("jobtittle",model.getJobtittle());
                        intent.putExtra("skills",model.getSkills());
                        intent.putExtra("description",model.getJobdescription());
                        intent.putExtra("salary",model.getJobsalary());
                        startActivity(intent);

                    }
                });





            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }










    public static  class SavedJobsPostsViewHolder extends RecyclerView.ViewHolder
    {
        TextView JobName,Company,PostedTime,PostedDate,UpdateStatus;
        CircleImageView Companylogo,eyelogo;
        View view;
        public SavedJobsPostsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            UpdateStatus=itemView.findViewById(R.id.UpdateStatus);
            view=itemView;
        }


        public void setJobtittle(String jobtittle)
        {
            JobName=(TextView)  view.findViewById(R.id.Apply_job_name);
            JobName.setText(jobtittle);
        }

        public void setCompanyname(String companyname)
        {
            Company=(TextView) view.findViewById(R.id.Apply_Job_Company) ;
            Company.setText(companyname);
        }

        public void setApplytime(String applytime)
        {
            PostedTime=(TextView) view.findViewById(R.id.Apply_Time);
            PostedTime.setText("Saved On Time : "+applytime);
        }

        public void setApplydate(String applydate)
        {
            PostedDate=(TextView) view.findViewById(R.id.Apply_Job);
            PostedDate.setText("Saved On Date : "+applydate);
        }




    }







}