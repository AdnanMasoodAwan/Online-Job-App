package com.example.job;

public class Job_Posts
{
      private String careid,date,time,image,jobtittle,companyname,skills,jobdescription,jobsalary;

      public  Job_Posts()
      {

      }

    public Job_Posts(String careid, String date, String time, String image, String jobtittle, String companyname, String skills, String jobdescription, String jobsalary)
    {
        this.careid = careid;
        this.date = date;
        this.time = time;
        this.image = image;
        this.jobtittle = jobtittle;
        this.companyname = companyname;
        this.skills = skills;
        this.jobdescription = jobdescription;
        this.jobsalary = jobsalary;
    }

    public String getCareid() {
        return careid;
    }

    public void setCareid(String careid) {
        this.careid = careid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJobtittle() {
        return jobtittle;
    }

    public void setJobtittle(String jobtittle) {
        this.jobtittle = jobtittle;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getJobsalary() {
        return jobsalary;
    }

    public void setJobsalary(String jobsalary) {
        this.jobsalary = jobsalary;
    }
}
