package com.ead.mscourse.specifications;

import com.ead.mscourse.models.CourseModel;
import com.ead.mscourse.models.CourseUserModel;
import com.ead.mscourse.models.LessonModel;
import com.ead.mscourse.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
          query.distinct(true);
          Root<ModuleModel> module = root;
          Root<CourseModel> course = query.from(CourseModel.class);
          Expression<Collection<ModuleModel>> coursesModules = course.get("modules");
          return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId), criteriaBuilder.isMember(module, coursesModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonModel> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(module.get("moduleId"), moduleId), criteriaBuilder.isMember(lesson, moduleLessons));
        };
    }

    public static Specification<CourseModel> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> courseProd = root.join("coursesUsers");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }
}
