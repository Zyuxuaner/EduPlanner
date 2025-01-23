package com.mengyunzhi.eduPlanner.service;

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
}
