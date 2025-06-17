package com.example.request_service.repository;

import com.example.request_service.entity.Request;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query(value = """
        SELECT * FROM request\s
        WHERE employee_id IN (:employeeIds)\s
        AND status = 'PENDING'
       \s""", nativeQuery = true)
    List<Request> findPendingRequestsByEmployeeIds(@Param("employeeIds") List<Integer> employeeIds);

    @Query(value = """
        SELECT * FROM request\s
        WHERE employee_id = :employeeId
        \s""", nativeQuery = true)
    List<Request> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Transactional
    @Modifying
    @Query(value = """
        UPDATE request
        SET status = :status
        WHERE id = :requestId
        """, nativeQuery = true)
    int updateRequest(
            @Param("requestId") Integer requestId,
            @Param("status") String status
    );

}
