package com.example.request_service.service.impl;

import com.example.request_service.dto.RequestSubmitDTO;
import com.example.request_service.dto.UpdateStatusDTO;
import com.example.request_service.entity.Request;
import com.example.request_service.exception.RequestNotFound;
import com.example.request_service.repository.RequestRepository;
import com.example.request_service.service.RequestService;
import com.example.request_service.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public void create(RequestSubmitDTO requestSubmitDTO) {

        requestRepository.save(MapperUtil.requestSubmitDTOToRequest(requestSubmitDTO));
    }

    @Override
    public List<Request> readRequestListByEmployeeIdList(List<Integer> employeeIds) {

        return requestRepository.findPendingRequestsByEmployeeIds(employeeIds);
    }

    @Override
    public List<Request> readRequestListByEmployeeId(Integer employeeId) {

        return requestRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void updateRequestStatusById(UpdateStatusDTO updateStatusDTO) {

        if (requestRepository.updateRequest(
                updateStatusDTO.getRequestId(),
                updateStatusDTO.getStatus().getDescription()
        ) != 1 ){
            throw new RequestNotFound();
        }

    }

}
