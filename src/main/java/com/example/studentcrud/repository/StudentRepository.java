
package com.example.studentcrud.repository;

import com.example.studentcrud.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT COUNT(s)>0 FROM student s WHERE s.rollNo = :rollNo")
    public boolean existByRollNo(@Param("rollNo") String rollo);
}
