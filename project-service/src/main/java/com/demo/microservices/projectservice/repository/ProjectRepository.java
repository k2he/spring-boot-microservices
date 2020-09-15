package com.demo.microservices.projectservice.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.demo.microservices.projectservice.model.ProjectInfo;
import com.demo.microservices.servicelibs.repository.BaseRepository;
/**
 * @author kaihe
 *
 */

@Repository
public interface ProjectRepository extends BaseRepository<ProjectInfo, Integer> {

  List<ProjectInfo> findAllByStatusIdNotOrderByCreatedOnDesc(int statusId);

  List<ProjectInfo> findAllByStatusIdNotOrderByDueDateAsc(int statusId);
}
