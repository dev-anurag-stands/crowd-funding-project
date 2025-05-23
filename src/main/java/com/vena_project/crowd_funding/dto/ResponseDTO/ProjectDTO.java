package com.vena_project.crowd_funding.dto.ResponseDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private Long projectId;

    private String title;

    private String description;

    private double totalAmountAsked;
  
    private double amountTillNow;

    private boolean profitable;

    private LocalDate createdOn;

}