package com.example.employee_service.repository;

import com.example.employee_service.dto.UniqueInfoDTO;
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

    @Query(value = """
        SELECT e.username, e.phone_number, e.email FROM employee e
        WHERE e.username = :username
        OR e.phone_number = :phoneNumber
        OR e.email = :email
        """, nativeQuery = true)
    List<UniqueInfoDTO> findUniqueInformation(
            @Param("username") String username,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email
    );

}