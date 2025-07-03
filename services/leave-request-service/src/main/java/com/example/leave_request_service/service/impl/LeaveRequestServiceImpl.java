package com.example.leave_request_service.service.impl;

import com.example.leave_request_service.client.*;
import com.example.leave_request_service.dto.*;
import com.example.leave_request_service.exception.*;
import com.example.leave_request_service.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
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
                    requestSubmitDTO.getStart_at(), requestSubmitDTO.getEnd_at()) + 1;

            Integer currentBalance = rcToBalanceService.getBalance(employeeId);

            int newBalance = currentBalance - requestedDays;

            if (newBalance < 0) throw new BalanceNotEnoughException();
            else rcToBalanceService.updateBalance(employeeId, newBalance);

            try {
                rcToRequestService.submitRequest(requestSubmitDTO);

            } catch (DownstreamServiceException ex){

                rcToBalanceService.updateBalance(employeeId, currentBalance);
                throw ex;
            }

        } else {

            rcToRequestService.submitRequest(requestSubmitDTO);

        }

    }

    @Override
    public void updateRequest(UpdateStatusDTO updateStatusDTO) {
        
        Request request = rcToRequestService.read(updateStatusDTO.getRequestId());

        Type type = request.getType();

        Status status = updateStatusDTO.getStatus();

        if (status == Status.PENDING){
            throw new InvalidPendingStatusException();

        } else {

            Integer employeeId = request.getEmployee_id();

            if (status == Status.REJECTED && type.isDeductedFromAnnualLeave()){

                int requestedDays = (int) ChronoUnit.DAYS.between(
                        request.getStart_at(),
                        request.getEnd_at()
                ) + 1;

                Integer currentBalance = rcToBalanceService.getBalance(employeeId);

                int newBalance = currentBalance + requestedDays;

                rcToBalanceService.updateBalance(employeeId, newBalance);

                try {
                    rcToRequestService.updateRequest(updateStatusDTO);

                } catch (DownstreamServiceException ex){
                    rcToBalanceService.updateBalance(employeeId, currentBalance);
                    throw ex;
                    
                }

            } else {
                rcToRequestService.updateRequest(updateStatusDTO);
            }

            notificationPublisher.sendEmailNotification(getNotifyMessage(status, request, employeeId));

        }
    }

    private NotifyMessage getNotifyMessage(Status status, Request request, Integer employeeId){
        String content = "Your Leave Request, type " + request.getType() +
                " created at " + request.getCreate_at() +
                " start from " + request.getStart_at() +
                " to " + request.getEnd_at() +
                ", was ";

        String subject = "LEAVE REQUEST ";

        if (status == Status.REJECTED){
            subject += "REJECTED";
            content += "REJECTED";

        } else {
            subject += "APPROVED";
            content += "APPROVED";

        }

        return NotifyMessage.builder()
                .to(rcToEmployeeService.getEmployeeEmailById(employeeId))
                .subject(subject)
                .content(content)
                .build();
    }

}
