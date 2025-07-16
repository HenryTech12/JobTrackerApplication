package org.jobtracker.app.JobTracker.mapper;

import org.jobtracker.app.JobTracker.dto.JobDTO;
import org.jobtracker.app.JobTracker.model.JobModel;
import org.jobtracker.app.JobTracker.response.JobResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JobMapper {

    @Autowired
    private ModelMapper mapper;

    public JobDTO convertToDTO(JobModel jobModel) {
        if(!Objects.isNull(jobModel))
           return mapper.map(jobModel,JobDTO.class);
        else
            return null;
    }

    public JobResponse convertToResponse(JobDTO jobDTO) {
        if(!Objects.isNull(jobDTO))
            return mapper.map(jobDTO,JobResponse.class);
        else
            return null;
    }

    public JobModel convertToModel(JobDTO jobDTO) {
        if(!Objects.isNull(jobDTO))
            return mapper.map(jobDTO, JobModel.class);
        else
            return null;
    }
}
