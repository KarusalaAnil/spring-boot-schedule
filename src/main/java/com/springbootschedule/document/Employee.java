package com.springbootschedule.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "emp_setup")
public class Employee {

    @Indexed
    private String name;
    private String mobileNo;
    private String civilId;
    private String civilId_expiry_date;
    private String visaType;
    private String visa_exp_date;
    private String passportNo;
    private String passport_expiry_date;
    private String dob;
    private String gender;
    private String salary;
//    private String joinDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date joinDate;
    private String maritalStatus;
    private String employee_code;
    private String emp_first_name;
    private String emp_last_name;
    private String sponsor;
    private String project_name;
    private String designation;

    private double food_allowance;
    private double phone_allowance;
    private double other_allowance;

    private String passport_issue_date;
    private String visa_issue_date;
    private String residency_issue_date;
}
