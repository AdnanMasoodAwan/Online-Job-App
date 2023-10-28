package com.example.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.paperdb.Paper;

public class Admin_panel_Activity extends AppCompatActivity
{
    CardView AddJobs;
    CardView DeleteJobs;
    CardView ShowJobs;
    CardView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        AddJobs=(CardView)findViewById(R.id.Add_Jobs);
        DeleteJobs=(CardView)findViewById(R.id.Delete_Jobs);
        ShowJobs=(CardView)findViewById(R.id.Show_Jobs);
        Logout=(CardView)findViewById(R.id.Logout );

        Paper.init(this);

        AddJobs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Add_job_Info_Activity.class);
                startActivity(intent);
            }
        });


        DeleteJobs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }

        });

        ShowJobs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Find_Jobs_Activity.class);
                startActivity(intent);
            }
        });


        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Paper.book().destroy();
                Intent intent=new Intent(getApplicationContext(),Sign_In_Activity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });




    }


}