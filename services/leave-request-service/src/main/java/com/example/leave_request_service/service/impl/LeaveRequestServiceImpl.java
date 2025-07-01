package com.example.leave_request_service.service.impl;

import com.example.leave_request_service.client.*;
import com.example.leave_request_service.dto.*;
import com.example.leave_request_service.exception.*;
import com.example.leave_request_service.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private RCToEmployeeService rcToEmployeeService;

    @Autowired
    private RCToBalanceService rcToBalanceService;

    @Autowired
    private RCToRequestService rcToRequestService;

    @Autowired
    private NotificationPublisher notificationPublisher;

    @Override
    public List<Request> readListByManagerId(Integer managerId) {

        List<Integer> employeeIds = rcToEmployeeService.getEmployeeIdsByManagerId(managerId);

        return rcToRequestService.readMyEmployeesRequest(employeeIds);
    }

    @Override
    public void submitRequest(Integer employeeId, RequestSubmitDTO requestSubmitDTO) {

        requestSubmitDTO.setEmployee_id(employeeId);

        if (requestSubmitDTO.getType().isDeductedFromAnnualLeave()){
            int requestedDays = (int) ChronoUnit.DAYS.between(
                    requestSubmitDTO.getStart_at(),
                    requestSubmitDTO.getEnd_at()
            ) + 1;

            Integer currentBalance = rcToBalanceService.getBalance(employeeId);

            int newBalance = currentBalance - requestedDays;

            if (newBalance < 0) throw new BalanceNotEnoughException();
            else rcToBalanceService.updateBalance(employeeId, newBalance);
        }

        rcToRequestService.submitRequest(requestSubmitDTO);
    }

    @Override
    public void updateRequest(UpdateStatusDTO updateStatusDTO) {
        
        Request request = rcToRequestService.read(updateStatusDTO.getRequestId());

        Status status = updateStatusDTO.getStatus();

        if (status == Status.PENDING){
            throw new InvalidPendingStatusException();
        } else {

            rcToRequestService.updateRequest(updateStatusDTO);

            Integer employeeId = request.getEmployee_id();

            String content = "Your Leave Request, type " + request.getType() +
                    " created at " + request.getCreate_at() +
                    " start from " + request.getStart_at() +
                    " to " + request.getEnd_at() +
                    ", was ";

            String subject = "LEAVE REQUEST ";

            if (status == Status.REJECTED){

                int requestedDays = (int) ChronoUnit.DAYS.between(
                        request.getStart_at(),
                        request.getEnd_at()
                ) + 1;

                Integer currentBalance = rcToBalanceService.getBalance(employeeId);

                int newBalance = currentBalance + requestedDays;

                rcToBalanceService.updateBalance(employeeId, newBalance);

                subject += "REJECTED";
                content += "REJECTED";

            } else {

                subject += "APPROVED";
                content += "APPROVED";
            }

            NotifyMessage message = NotifyMessage.builder()
                    .to(rcToEmployeeService.getEmployeeEmailById(employeeId))
                    .subject(subject)
                    .content(content)
                    .build();

            System.out.println(message);
            notificationPublisher.sendEmailNotification(message);

        }
    }

}
