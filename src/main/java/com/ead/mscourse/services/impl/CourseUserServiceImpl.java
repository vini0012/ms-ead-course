package com.ead.mscourse.services.impl;

import com.ead.mscourse.repositories.CourseUserRepository;
import com.ead.mscourse.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

}
