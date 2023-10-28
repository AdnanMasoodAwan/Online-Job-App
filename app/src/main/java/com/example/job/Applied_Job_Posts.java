package com.example.job;

public class Applied_Job_Posts
{
    private  String careid,companyname,jobdescription,jobsalary,jobtittle,skills,status,applytime,applydate;

    public  Applied_Job_Posts()
    {

    }
    public Applied_Job_Posts(String careid, String companyName, String jobdescription, String jobsalary, String jobtittle, String skills, String status, String applytime, String applydate) {
        this.careid = careid;
        this.companyname = companyName;
        this.jobdescription = jobdescription;
        this.jobsalary = jobsalary;
        this.jobtittle = jobtittle;
        this.skills = skills;
        this.status = status;
        this.applytime = applytime;
        this.applydate = applydate;
    }

    public String getCareid() {
        return careid;
    }

    public void setCareid(String careid) {
        this.careid = careid;
    }


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public String getJobtittle() {
        return jobtittle;
    }

    public void setJobtittle(String jobtittle) {
        this.jobtittle = jobtittle;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
    }
}
