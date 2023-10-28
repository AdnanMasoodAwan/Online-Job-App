package com.example.job;

import static com.example.job.R.layout.job_posts_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.nio.channels.Pipe;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Find_Jobs_Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    CircleImageView circleImageView,ProfilePhoto,SearcIcon;
    TextView User;
    SearchView UserInput;

    Button Apply,Discover,Save;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_jobs);



        ProfilePhoto=(CircleImageView)findViewById(R.id.profile_image);
        Apply=(Button)findViewById(R.id.button2) ;
        Discover=(Button)findViewById(R.id.button0) ;
        Save=(Button) findViewById(R.id.button1);
        User=(TextView) findViewById(R.id.Textview);
        UserInput=(SearchView)findViewById(R.id.Search_Job_Info);
        recyclerView = (RecyclerView) findViewById(R.id.CarInfoList);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Job Information");
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Find_Jobs_Activity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        






        Apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), Applied_Jobs_Activity.class);
                startActivity(intent);

            }
        });


        Discover.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), Find_Jobs_Activity.class);
                startActivity(intent);
            }

        });

        Save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), SavedJobsActivity.class);
                startActivity(intent);
            }

        });


        UserInput.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                onStart(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                onStart(newText);
                return false;
            }
        });




    }




    protected void onStart(String searchViewText)
    {

        super.onStart();

        Query query = databaseReference.orderByChild("jobtittle")
                .startAt(searchViewText)
                .endAt(searchViewText + "\uf8ff");

        FirebaseRecyclerOptions<Job_Posts> options=
                new FirebaseRecyclerOptions.Builder<Job_Posts>()
                        .setQuery(query,Job_Posts.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Job_Posts,PostsViewHolder>(options)
        {

            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.posts_layout, parent, false);

                return new PostsViewHolder(view);
            }


            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position,  Job_Posts model)
            {

                String PostKey = getRef(position).getKey();

                holder.setJobtittle(model.getJobtittle());
                holder.setCompanyname(model.getCompanyname());
                holder.setJobdescription(model.getJobdescription());
                holder.setJobsalary(model.getJobsalary());
                holder.setImage(model.getImage(),getApplicationContext());
                Toast.makeText(getApplicationContext(),model.getJobtittle(), Toast.LENGTH_SHORT).show();

                holder.view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent=new Intent(getApplicationContext(),Job_Details_Activity.class);
                        intent.putExtra("postkey",PostKey);
                        intent.putExtra("jobtittle",model.getJobtittle());
                        intent.putExtra("company",model.getCompanyname());
                        intent.putExtra("image",model.getImage());
                        intent.putExtra("skills",model.getSkills());
                        intent.putExtra("description",model.getJobdescription());
                        intent.putExtra("salary",model.getJobsalary());
                        intent.putExtra("time",model.getTime());
                        intent.putExtra("date",model.getDate());
                        startActivity(intent);

                    }
                });





            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }






    public static  class PostsViewHolder extends RecyclerView.ViewHolder
    {
       TextView JobName,Company,Skills,JobDescription,Salary,PostedTime;
        CircleImageView Companylogo,eyelogo;
        View view;
        public PostsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view=itemView;
        }

        public void setImage(String image, Context context)
        {
            Companylogo = (CircleImageView) view.findViewById(R.id.Friend_profile_image);
                Picasso.get().load(image).into(Companylogo);
        }

        public void setJobtittle(String jobtittle)
        {
            JobName=(TextView)  view.findViewById(R.id.Friend_user_name);
            JobName.setText(jobtittle);
        }

        public void setCompanyname(String companyname)
        {
            Company=(TextView) view.findViewById(R.id.Firend_user_Date) ;
            Company.setText(companyname);
        }

        public void setJobdescription(String jobdescription)
        {
            JobDescription=(TextView) view.findViewById(R.id.post_image);
            JobDescription.setText(jobdescription);
        }

        public void setTime(String time)
        {
            PostedTime=(TextView) view.findViewById(R.id.dislike_post);
            PostedTime.setText(time);
        }

        public void setJobsalary(String jobsalary)
        {
            Salary=(TextView)  view.findViewById(R.id.Salary);
            Salary.setText(jobsalary);
        }


    }






}