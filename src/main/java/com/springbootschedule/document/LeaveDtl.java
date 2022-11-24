package com.springbootschedule.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "leave_dtl")
public class LeaveDtl {

    @Id
    private String id;
    private String leave_dtl_code;
    private String emp_code;
    private List<LeaveSetup> setupList;
}
