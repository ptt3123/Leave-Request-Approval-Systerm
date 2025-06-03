package com.example.employee_service.repository;

import com.example.employee_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByUsername(String username);

    @Query(value = """
        SELECT e.id FROM employee e
        WHERE e.team_id = (
            SELECT team_id FROM employee WHERE id = :managerId
        ) AND e.id != :managerId
        """, nativeQuery = true)
    List<Integer> findIdsByManagerId(@Param("managerId") Integer managerId);

}