package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.RequestDTO.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.ContributionRepository;
import com.vena_project.crowd_funding.service.ContributionService;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   ProjectService projectService,
                                   UserService userService) {
        this.contributionRepository = contributionRepository;
        this.projectService = projectService;
        this.userService = userService;
    }


     // Add a contribution for investment (profitable) type project.
     @Override
     public ContributionResponseDTO makeContribution(Long projectId, ContributionRequestDTO dto) {
         User contributor = userService.getUserById(dto.getUserId());

         if (contributor.getRole() == UserRole.ADMIN) {
             throw new IllegalArgumentException("Admins are not allowed to make contributions.");
         }

         // Get actual project entity
         Project projectEntity = projectService.getProjectEntityById(projectId);

         if (projectEntity.getProjectStatus() != ProjectStatus.APPROVED) {
             throw new IllegalArgumentException("Cannot contribute to a project that is not approved.");
         }

         if (projectEntity.getAmountTillNow() >= projectEntity.getTotalAmountAsked()) {
             throw new IllegalArgumentException("Funding target already reached. Cannot accept more contributions.");
         }

         Contribution contribution = new Contribution();
         contribution.setContributor(contributor);
         contribution.setProject(projectEntity);
         contribution.setAmountGiven(dto.getAmountGiven());
         contribution.setDate(LocalDate.now());

         projectService.incrementAmountTillNow(projectId, dto.getAmountGiven());

         Contribution saved = contributionRepository.save(contribution);

         ContributionResponseDTO responseDTO = new ContributionResponseDTO();
         responseDTO.setFromContribution(saved, contributor, projectService.getProjectById(projectId));
         return responseDTO;
     }


    @Override
    public List<ContributionResponseDTO> getUserContributions(Long userId) {
        User user = userService.getUserById(userId);

        if(user.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot have any projects");
        }

        List<Contribution> contributions = contributionRepository.findByContributor_Id(userId);

        return contributions.stream().map(c -> {
            ProjectResponseDTO project = projectService.getProjectById(c.getProject().getProjectId());
            ContributionResponseDTO dto = new ContributionResponseDTO();
            dto.setFromContribution(c, user, project);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ContributionResponseDTO> getContributionsOnProject(Long projectId, Long requesterId) {
        User requester = userService.getUserById(requesterId);

        // Get actual Project entity to access createdBy
        Project projectEntity = projectService.getProjectEntityById(projectId);

        if (requester.getRole() != UserRole.ADMIN)
        {
            if (!projectEntity.getCreatedBy().getId().equals(requesterId)) {
                throw new IllegalArgumentException("Not authorized to view contributions on this project.");
            }
        }

        List<Contribution> contributions = contributionRepository.findByProject_ProjectId(projectId);

        ProjectResponseDTO projectResponseDTO = projectService.getProjectById(projectId);

        return contributions.stream().map(c -> {
            User contributor = userService.getUserById(c.getContributor().getId());
            ContributionResponseDTO dto = new ContributionResponseDTO();
            dto.setFromContribution(c, contributor, projectResponseDTO);
            return dto;
        }).collect(Collectors.toList());
    }
}

