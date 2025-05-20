package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.ContributionRepository;
import com.vena_project.crowd_funding.repository.ProjectRepository;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.ContributionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   ProjectRepository projectRepository,
                                   UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ContributionResponseDTO addInvestmentContribution(Long userId, Long projectId, ContributionRequestDTO requestDTO) {
        return addContribution(userId, projectId, requestDTO, true);
    }

    @Override
    public ContributionResponseDTO addDonationContribution(Long userId, Long projectId, ContributionRequestDTO requestDTO) {
        return addContribution(userId, projectId, requestDTO, false);
    }

    private ContributionResponseDTO addContribution(Long userId, Long projectId, ContributionRequestDTO requestDTO, boolean isProfitableExpected) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        if (!project.getProjectStatus().equals(ProjectStatus.APPROVED)) {
            throw new RuntimeException("Only approved projects can receive contributions");
        }

        if (project.isProfitable() != isProfitableExpected) {
            String expectedType = isProfitableExpected ? "profitable (investment)" : "non-profitable (donation)";
            String actualType = project.isProfitable() ? "profitable" : "non-profitable";
            throw new RuntimeException("Invalid contribution type: You can only contribute to a " + expectedType +
                    " project, but this project is " + actualType + ".");
        }

        if (project.getAmountTillNow() >= project.getTotalAmountAsked()) {
            throw new RuntimeException("Project has already reached its target amount and is considered disbursed.");
        }

        User contributor = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Contributor not found with ID: " + userId));

        if (contributor.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Admins are not allowed to make contributions");
        }

        Contribution contribution = new Contribution();
        contribution.setProject(project);
        contribution.setContributor(contributor);
        contribution.setAmountGiven(requestDTO.getAmountGiven());
        contribution.setDate(LocalDate.now());

        contributionRepository.save(contribution);

        project.setAmountTillNow(project.getAmountTillNow() + requestDTO.getAmountGiven());
        projectRepository.save(project);

        ContributionResponseDTO responseDTO = new ContributionResponseDTO();
        responseDTO.setContributionId(contribution.getId());
        responseDTO.setContributorName(contribution.getContributor().getName());
        responseDTO.setProjectTitle(contribution.getProject().getTitle());
        responseDTO.setProjectDescription(contribution.getProject().getDescription());
        responseDTO.setAmountGiven(contribution.getAmountGiven());
        responseDTO.setContributionDate(contribution.getDate());


        return responseDTO;
    }
}




