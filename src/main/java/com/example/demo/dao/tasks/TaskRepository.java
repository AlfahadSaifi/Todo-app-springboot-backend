package com.example.demo.dao.tasks;

import com.example.demo.entities.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByTaskDate(Date taskDate);

    Tasks findByTaskDateAndTaskTime(Date taskDate, Time taskTime);

    @Transactional
    @Modifying
    @Query("DELETE FROM Tasks t WHERE t.taskDate = :taskDate AND t.taskTime = :taskTime")
    void deleteByTaskDateAndTaskTime(@Param("taskDate") Date taskDate, @Param("taskTime") Time taskTime);
}

