package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.RequestDTO.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.exception.AccessDeniedForAdminException;
import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.ContributionRepository;
import com.vena_project.crowd_funding.service.ContributionService;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContributionServiceImpl implements ContributionService {

    private static final Logger logger = LoggerFactory.getLogger(ContributionServiceImpl.class);

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

    @Override
    public ContributionResponseDTO makeContribution(Long projectId, ContributionRequestDTO dto) {
        logger.info("Initiating contribution process for Project ID: {} by User ID: {}", projectId, dto.getUserId());

        User contributor = userService.getUserById(dto.getUserId());
        Project projectEntity = projectService.findProjectById(projectId);

        if (contributor.getRole() == UserRole.ADMIN) {
            logger.warn("Admin tried to contribute. Access denied.");
            throw new AccessDeniedForAdminException("Admins are not allowed to make contributions.");
        }
        if (projectEntity.getProjectStatus() != ProjectStatus.APPROVED) {
            logger.warn("Contribution rejected - Project ID {} is not approved.", projectId);
            throw new IllegalArgumentException("User can only contribute to an approved project.");
        }

        if (projectEntity.getAmountTillNow() >= projectEntity.getTotalAmountAsked()) {
            logger.warn("Contribution rejected - Project ID {} has reached its funding goal.", projectId);
            throw new IllegalArgumentException("Asked amount is already reached. Cannot accept more contributions.");
        }

        Contribution contribution = new Contribution();
        contribution.setContributor(contributor);
        contribution.setProject(projectEntity);
        contribution.setAmountGiven(dto.getAmountGiven());
        contribution.setDate(LocalDate.now());

        projectService.incrementAmountTillNow(projectId, dto.getAmountGiven());
        Contribution saved = contributionRepository.save(contribution);

        logger.info("Contribution of amount {} by user ID {} saved successfully for project ID {}",
                dto.getAmountGiven(), dto.getUserId(), projectId);

        ContributionResponseDTO responseDTO = new ContributionResponseDTO();
        responseDTO.setFromContribution(saved, contributor, projectService.getProjectById(projectId));
        return responseDTO;
    }

    @Override
    public List<ContributionResponseDTO> getUserContributions(Long userId) {
        logger.info("Fetching contributions for User ID: {}", userId);

        User user = userService.getUserById(userId);

        if (user.getRole() == UserRole.ADMIN) {
            logger.warn("Admin tried to fetch contributions. Not allowed.");
            throw new IllegalArgumentException("Admins are not allowed to contribute, so they do not have any contributions.");
        }

        List<Contribution> contributions = contributionRepository.findByContributorId(userId);

        return contributions.stream().map(c -> {
            ProjectResponseDTO project = projectService.getProjectById(c.getProject().getProjectId());
            ContributionResponseDTO dto = new ContributionResponseDTO();
            dto.setFromContribution(c, user, project);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ContributionResponseDTO> getContributionsOnUserProject(Long projectId, Long requesterId) {
        logger.info("Requester ID {} requesting contributions on Project ID {}", requesterId, projectId);

        User requester = userService.getUserById(requesterId);
        Project projectEntity = projectService.findProjectById(projectId);

        if (requester.getRole() != UserRole.ADMIN) {
            logger.warn("Unauthorized access attempt by non-admin user ID {}", requesterId);
            throw new AccessDeniedForAdminException("Admin users do not own any projects and cannot view contributions.");
        }

        if (!projectEntity.getCreatedBy().getId().equals(requesterId)) {
            logger.warn("Admin ID {} tried to access contributions of a project not created by them.", requesterId);
            throw new IllegalArgumentException("User not authorized to view contributions on this project.");
        }

        List<Contribution> contributions = contributionRepository.findByProjectId(projectId);
        ProjectResponseDTO projectResponseDTO = projectService.getProjectById(projectId);

        logger.info("Returning {} contributions on project ID {}", contributions.size(), projectId);

        return contributions.stream().map(c -> {
            User contributor = userService.getUserById(c.getContributor().getId());
            ContributionResponseDTO dto = new ContributionResponseDTO();
            dto.setFromContribution(c, contributor, projectResponseDTO);
            return dto;
        }).collect(Collectors.toList());
    }
}
