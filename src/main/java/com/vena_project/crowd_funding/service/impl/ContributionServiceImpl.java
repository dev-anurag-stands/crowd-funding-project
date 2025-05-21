package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ProjectDTO;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.ContributionRepository;
import com.vena_project.crowd_funding.service.ContributionService;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final ProjectService projectService;
    private final UserService userService;

    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   ProjectService projectService,
                                   UserService userService) {
        this.contributionRepository = contributionRepository;
        this.projectService = projectService;
        this.userService = userService;
    }


     // Add a contribution for investment (profitable) type project.
    @Override
    public ContributionResponseDTO addInvestmentContribution(Long userId, Long projectId, Double amountGiven) {
        return addContribution(userId, projectId, amountGiven, true);
    }


     //Add a contribution for donation (non-profitable) type project.
    @Override
    public ContributionResponseDTO addDonationContribution(Long userId, Long projectId, Double amountGiven) {
        return addContribution(userId, projectId, amountGiven, false);
    }
     /*
     Core logic to handle contributions (donation or investment).
     Validates user role, project status, contribution type, and
     atomically increments the project's funded amount in the database.
     */
    private ContributionResponseDTO addContribution(Long userId, Long projectId, Double amountGiven, boolean isProfitableExpected) {
        // Fetch project details by ID
        Project project = projectService.findProjectById(projectId);

        // Ensure only approved projects can receive contributions
        if (!project.getProjectStatus().equals(ProjectStatus.APPROVED)) {
            throw new RuntimeException("Only approved projects can receive contributions.");
        }

        // Ensure user is contributing to the correct project type
        if (project.isProfitable() != isProfitableExpected) {
            String expectedType = isProfitableExpected ? "profitable (investment)" : "non-profitable (donation)";
            String actualType = project.isProfitable() ? "profitable" : "non-profitable";
            throw new RuntimeException("Invalid contribution type: You can only contribute to a " + expectedType +
                    " project, but this project is " + actualType + ".");
        }

        // Prevent contributions if target amount is already reached
        if (project.getAmountTillNow() >= project.getTotalAmountAsked()) {
            throw new RuntimeException("Project has already reached its target amount and is considered disbursed.");
        }

        // Fetch contributor user details
        User contributor = userService.userInfo(userId);

        // Prevent Admins from contributing
        if (contributor.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Admins are not allowed to make contributions.");
        }

        // Create and populate Contribution entity
        Contribution contribution = new Contribution();
        contribution.setContributorId(contributor);
        contribution.setProjectId(project);
        contribution.setAmountGiven(amountGiven);
        contribution.setDate(LocalDate.now());

        // Save contribution to the database
        Contribution savedContribution = contributionRepository.save(contribution);

        // increase the current amount of the project
        projectService.incrementAmountTillNow(projectId, amountGiven);

        // Build and return the response DTO
        ContributionResponseDTO responseDTO = new ContributionResponseDTO();
        responseDTO.setFromContribution(savedContribution, contributor, project);

        return responseDTO;
    }
}
