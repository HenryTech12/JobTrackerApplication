package org.jobtracker.app.JobTracker.repository;

import org.jobtracker.app.JobTracker.model.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobRepository extends JpaRepository<JobModel,Long> {

    List<JobModel> findJobByUserId(Long userId);

}
