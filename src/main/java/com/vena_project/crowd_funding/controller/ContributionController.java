package com.vena_project.crowd_funding.controller;
import com.vena_project.crowd_funding.dto.RequestDTO.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.service.ContributionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    // POST /contribution/{projectId}
    @PostMapping("/{projectId}")
    public ResponseEntity<ContributionResponseDTO> makeContribution(
            @PathVariable Long projectId,
            @Valid @RequestBody ContributionRequestDTO contributionRequestDTO) {
        ContributionResponseDTO responseDTO = contributionService.makeContribution(projectId, contributionRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // GET /contribution/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<ContributionResponseDTO>> getUserContributions(
            @PathVariable Long userId) {
        List<ContributionResponseDTO> responseList = contributionService.getUserContributions(userId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // GET /contribution/{projectId}/{userId}
    @GetMapping("/{userId}/{projectId}")
    public ResponseEntity<List<ContributionResponseDTO>> getContributionsOnUserProject(
            @PathVariable Long projectId,
            @PathVariable Long userId) {
        List<ContributionResponseDTO> responseList = contributionService.getContributionsOnProject(projectId, userId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

}


