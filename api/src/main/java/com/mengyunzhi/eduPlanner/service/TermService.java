package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.Term;

import java.util.List;
import java.util.Optional;

public interface TermService {
    /**
     * 激活学期
     * @param termId 待激活的学期id
     * @return Response类型的返回值
     */
    Response<String> active(Long termId);

    Response<Void> deleteTerm(Long id);

    Term getTermById(Long id);

    Response<Term> updateTerm (Long id, Term term);

    /**
     * 新增学期
     * @param term
     * @return
     */
    Response<Term> save(Term term);

    List<Term> search(Long schoolId, String searchName);

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

    /**
     * 获取该学校下激活学期的所有周数以及所有学生
     * @param schoolId 学校id
     * @return 响应
     */
    Response<TermDto.TermAndWeeksAndStudentsResponse> getTermAndWeeksAndStudents(Long schoolId);

    /**
     * 根据学期获取当前学期的总周数
     * @param term 学期id
     * @return totalWeeks
     */
    Long getTotalWeeksByTerm(Term term);
}
