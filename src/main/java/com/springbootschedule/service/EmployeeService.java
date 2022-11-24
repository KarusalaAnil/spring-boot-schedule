package com.springbootschedule.service;

import com.springbootschedule.Util.CommonUtil;
import com.springbootschedule.document.Employee;
import com.springbootschedule.document.LeaveDtl;
import com.springbootschedule.document.LeaveSetup;
import com.springbootschedule.repository.EmployeeRepository;
import com.springbootschedule.repository.LeaveDtlRepository;
import com.springbootschedule.repository.LeavesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveDtlRepository leaveDtlRepository;

    @Autowired
    private LeavesRepository leaveSetupRepository;

    @Autowired
    @Lazy
    MongoTemplate mongoTemplate;


    @Scheduled(cron = "${cron_interval}")
    public void saveLeaveDetailAndUpdate() {
        log.info("Scheduler is running");

        /** initially Leave Details are empty then we can save all setup info in details table*/
        /** Leave Details*/
        List<LeaveDtl> leaveDtlList = leaveDtlRepository.findAll();
        if (leaveDtlList == null || leaveDtlList.size() == 0) { //ex-1
            log.info("if condition leaveDtlList----="+leaveDtlList);

            emptyLeaveDetails(leaveDtlList);
        } else {
            log.info("else condition----="+leaveDtlList);
            List<Employee> employeeList = employeeRepository.findAll();
            List<LeaveSetup> leaveSetupList = leaveSetupRepository.findAll();

            log.info("employeeList="+employeeList);
            log.info("leaveSetupList-{}-"+leaveSetupList);

            /** employee loop*/
            employeeList.stream().forEach(employee -> {

                /** leaveDtl loop*/
                leaveDtlList.stream().forEach(leaveDtl -> {
                    if (employee.getEmployee_code().equalsIgnoreCase(leaveDtl.getEmp_code())) {


//                        if(leaveSetupList.size() != leaveDtl.getSetupList().size()){

                        /** leave Setup loop*/
                        leaveSetupList.stream().forEach(leaveSetup -> {
                            boolean status = checkLeaveTypeCode(leaveDtl, leaveSetup);

                            if (!status) { // if false then need to save leave details to leave setup save leave details
                                List<LeaveSetup> saveLeaveSetup = leaveDtl.getSetupList();
                                if (saveLeaveSetup == null)
                                    saveLeaveSetup = new ArrayList<>();
                                else
                                    saveLeaveSetup.add(leaveSetup);
                                Query query = new Query();
                                query.addCriteria(Criteria.where("id").is(leaveDtl.getId()));
                                Update update = new Update();
                                update.set("setupList", saveLeaveSetup);
                                mongoTemplate.updateFirst(query, update, LeaveDtl.class);
                            }
//                                if(leaveSetup.getLeave_type_code().equalsIgnoreCase(leaveDtl.get))


                        });
//                        }

                    }

                });

            });
        }


    }

    public boolean checkEmployeeCodeSetup(LeaveDtl leaveDtl, List<LeaveSetup> leaveSetupList, Employee employee) {
        boolean status = false;
        /** employee existed or not if not save leave setup in details*/
        if (!leaveDtl.getEmp_code().equalsIgnoreCase(employee.getEmployee_code())) {
            status = true;
        }
        return status;
    }

    public void emptyLeaveDetails(List<LeaveDtl> leaveDtlList) {
        log.info("saving ---");
//        if (leaveDtlList == null || leaveDtlList.size() == 0) { //ex-1
        /** Employee Details*/
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList != null && employeeList.size() > 0) {
            /** Leave Setup*/
            List<LeaveSetup> leaveSetupList = leaveSetupRepository.findAll();
            if (leaveSetupList != null && leaveSetupList.size() > 0) {
                IntStream.range(0, employeeList.size()).forEach(value -> {
                    log.info("saving --value-=" + value);
                    Employee employee = employeeList.get(value);
                    LeaveDtl leaveDtl = new LeaveDtl();
                    leaveDtl.setEmp_code(employee.getEmployee_code());
                    leaveDtl.setLeave_dtl_code(CommonUtil.generatorCode((value + 1), "DTL"));
                    leaveDtl.setSetupList(leaveSetupList);
                    leaveDtlRepository.save(leaveDtl);
                });
            }
        }

//        }
    }

    public boolean checkLeaveTypeCode(LeaveDtl leaveDtl, LeaveSetup leaveSetup) {
//        boolean status = false;
        List<LeaveSetup> list = null;
        if (leaveDtl.getSetupList() != null && leaveDtl.getSetupList().size() >0)
            list = leaveDtl.getSetupList().stream().filter(leaveDtlSetUp -> leaveSetup.getLeave_type_code().equalsIgnoreCase(leaveDtlSetUp.getLeave_type_code())).collect(Collectors.toList());
        return (list != null && list.size() > 0) ? true : false;
    }
}
