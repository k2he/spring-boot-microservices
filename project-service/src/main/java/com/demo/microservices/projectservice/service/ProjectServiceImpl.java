package com.demo.microservices.projectservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.demo.microservices.projectservice.model.Enums;
import com.demo.microservices.projectservice.model.Enums.PStatus;
import com.demo.microservices.projectservice.model.ProjectInfo;
import com.demo.microservices.projectservice.repository.ProjectRepository;
import com.demo.microservices.projectservice.util.ProjectConstants;
import com.demo.microservices.servicelibs.exception.ResourceNotFoundException;
import com.demo.microservices.servicelibs.service.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  @NonNull
  private ProjectRepository projectRepository;

  @NonNull
  private MessageService messageServie;

  String userID = "1";// This should get from JWT token

  @Override
  public List<ProjectInfo> getAllProjects() {
    return projectRepository.findAllByStatusIdNotOrderByDueDateAsc(PStatus.DELETED.getValue());
  }

  @Override
  public ProjectInfo createProject(ProjectInfo info) {
    return projectRepository.save(info);
  }

  @Override
  public ProjectInfo getProjectById(Integer id) {
    String message = messageServie.getMessage(ProjectConstants.PROJECT_NOT_FOUND);
    return projectRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(ProjectConstants.PROJECT_NOT_FOUND, message));
  }

  @Override
  public ProjectInfo updateProject(ProjectInfo info) {
    String message = messageServie.getMessage(ProjectConstants.PROJECT_NOT_FOUND);
    ProjectInfo project = projectRepository.findById(info.getProjectId()).orElseThrow(
        () -> new ResourceNotFoundException(ProjectConstants.PROJECT_NOT_FOUND, message));

    project.setProjectName(info.getProjectName());
    project.setProjectSummary(info.getProjectSummary());
    project.setDueDate(info.getDueDate());
    project.setEstimatedCost(info.getEstimatedCost());
    project.setProjectStatus(info.getProjectStatus());

    return projectRepository.save(project);
  }

  @Override
  public void deleteProject(Integer id) {
    ProjectInfo project = projectRepository.findById(id).get();
    if (project != null) {
      project.setStatusId(Enums.PStatus.DELETED.getValue());
      projectRepository.save(project);
    }
  }

}
