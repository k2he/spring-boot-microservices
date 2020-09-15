package com.demo.microservices.projectservice.service;

import java.util.List;
import com.demo.microservices.projectservice.model.ProjectInfo;
/**
 * @author kaihe
 *
 */

public interface ProjectService {
  
  List<ProjectInfo> getAllProjects();

  ProjectInfo createProject(ProjectInfo info);

  ProjectInfo getProjectById(Integer id);

  ProjectInfo updateProject(ProjectInfo info);

  void deleteProject(Integer id);
}
