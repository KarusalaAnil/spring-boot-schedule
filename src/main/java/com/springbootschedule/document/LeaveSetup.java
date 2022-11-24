package com.springbootschedule.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "leave_setup")
public class LeaveSetup  {

    private String leave_code;
    private String leave_type_code;
    private String leave_type_name;
    private int available_leaves;

}
