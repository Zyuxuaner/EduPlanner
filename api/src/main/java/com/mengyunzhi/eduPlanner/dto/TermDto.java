package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


public class TermDto {
    @Data
    public static class TermAndWeeksResponse {
        private Term term;
        private List<Integer> weeks;
    }

    @Data
    public static class SchoolIdAndStartTimeResponse {
        private Long schoolId;
        private Timestamp startTime;
        private String name;
    }

    @Data
    public static class TermAndWeeksAndStudentsResponse {
        private Term term;
        private List<Integer> weeks;
        private List<Student> students;
    }
}
