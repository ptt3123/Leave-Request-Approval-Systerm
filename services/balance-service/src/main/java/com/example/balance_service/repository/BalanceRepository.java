package com.example.balance_service.repository;

import com.example.balance_service.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    @Query(value = """
    SELECT EXISTS (
        SELECT 1
        FROM balance lb
        WHERE lb.employee_id = :employeeId
        AND YEAR(lb.create_at) = YEAR(CURRENT_DATE)
    )
    """, nativeQuery = true)
    Long existsBalanceThisYearByEmployeeIdRaw(
            @Param("employeeId") Integer employeeId
    );

    @Query(value = """
        SELECT lb.balance
        FROM balance lb
        WHERE lb.employee_id = :employeeId
        AND YEAR(lb.create_at) = YEAR(CURRENT_DATE)
        """, nativeQuery = true)
    Optional<Integer> findCurrentYearBalanceByEmployeeId(
            @Param("employeeId") Integer employeeId
    );

    @Transactional
    @Modifying
    @Query(value = """
        UPDATE balance
        SET balance = :newBalance
        WHERE employee_id = :employeeId
        AND YEAR(create_at) = YEAR(CURRENT_DATE)
        """, nativeQuery = true)
    int updateLeaveBalanceThisYear(
            @Param("employeeId") Integer employeeId,
            @Param("newBalance") Integer newBalance
    );

}
