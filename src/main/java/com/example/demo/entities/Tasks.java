package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task;

    @Column(name = "task_date")
    private Date taskDate;

    private String edited;

    @Column(name = "task_time")
    private Time taskTime;

}
