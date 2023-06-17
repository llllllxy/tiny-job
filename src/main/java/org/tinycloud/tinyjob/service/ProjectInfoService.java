package org.tinycloud.tinyjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.mapper.ProjectInfoMapper;

@Service
public class ProjectInfoService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;


}
