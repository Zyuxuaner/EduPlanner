package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.Term;

import java.util.List;

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
}
