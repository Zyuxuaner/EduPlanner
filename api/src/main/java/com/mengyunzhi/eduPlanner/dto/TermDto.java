package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Term;
import lombok.Data;

import java.util.List;


public class TermDto {
    @Data
    public static class TermAndWeeksResponse {
        private Term term;
        private List<Integer> weeks;
    }
}
