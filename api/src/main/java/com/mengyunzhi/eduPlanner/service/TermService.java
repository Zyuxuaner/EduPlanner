package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.Term;

import java.util.List;
import java.util.Optional;

public interface TermService {
    /**
     * 新增学期
     * @param term
     * @return
     */
    Term save(Term term);

    /**
     * 获取所有学期
     *
     * @return List<Term>
     */
    List<Term> getAll();

    /**
     * 检查当前登录用户所在学校下是否有激活学期
     * @param schoolId 当前登录学生的学校id
     * @param status 激活状态 1L
     * @return Optional<Term>
     */
    Optional<Term> checkTermActive(Long schoolId, Long status);

    /**
     * 根据学校 id 获取该学期下总周数
     * @param schoolId 学校 id
     * @return responseTermAndWeek
     */
    Response<TermDto.TermAndWeeksResponse> getTermAndWeeks (Long schoolId);

    /**
     * 获取所有学期id，以及激活学期的开学日期
     * @return SchoolIdAndStartTimeResponse数组
     */
    List<TermDto.SchoolIdAndStartTimeResponse> getSchoolIdAndStartTime ();
}
