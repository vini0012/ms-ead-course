package com.ead.mscourse.repositories;

import com.ead.mscourse.models.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {
}
