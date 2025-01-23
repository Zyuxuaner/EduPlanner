package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CourseInfoRepository extends CrudRepository<CourseInfo, Long> {
    @Query("SELECT ci FROM CourseInfo ci JOIN ci.course c WHERE c.term.startTime <= :endTime " +
            "AND c.term.endTime >= :startTime AND ci.day = :dayOfWeek AND ci.begin <= :endHour " +
            "AND (ci.begin + ci.length) > :beginHour")
    List<CourseInfo> findCoursesByDateRangeAndStudent(@Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
                                                      @Param("dayOfWeek") Long day,
                                                      @Param("begin") Long begin,
                                                      @Param("end") Long end,
                                                      @Param("studentId") Long studentId);
}
