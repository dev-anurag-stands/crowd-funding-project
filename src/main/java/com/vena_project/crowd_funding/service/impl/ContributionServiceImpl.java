package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.User;
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
    public ContributionResponseDTO addInvestmentContribution(ContributionRequestDTO requestDTO) {
        return addContribution(requestDTO, true); // true = expecting a profitable project
    }

    @Override
    public ContributionResponseDTO addDonationContribution(ContributionRequestDTO requestDTO) {
        return addContribution(requestDTO, false); // false = expecting a non-profitable project
    }

    private ContributionResponseDTO addContribution(ContributionRequestDTO requestDTO, boolean isProfitableExpected) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + requestDTO.getProjectId()));

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

        User contributor = userRepository.findById(requestDTO.getContributorId())
                .orElseThrow(() -> new RuntimeException("Contributor not found with ID: " + requestDTO.getContributorId()));

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
        responseDTO.setContributorName(contributor.getName());
        responseDTO.setProjectTitle(project.getTitle());
        responseDTO.setAmountGiven(contribution.getAmountGiven());
        responseDTO.setContributionDate(contribution.getDate());

        return responseDTO;
    }
}

