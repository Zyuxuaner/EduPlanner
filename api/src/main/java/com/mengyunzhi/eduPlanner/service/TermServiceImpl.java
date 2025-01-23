package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TermServiceImpl implements TermService {
    private final static Logger logger = LoggerFactory.getLogger(TermServiceImpl.class);

    private TermRepository termRepository;

    @Autowired
    private TermServiceImpl(TermRepository termRepository) {
       this.termRepository = termRepository;
    }

    @Override
    public Term save(Term term) {
        return this.termRepository.save(term);
    }

    @Override
    public List<Term> getAll() {
        return this.termRepository.findAll();
    }

    @Override
    public Optional<Term> checkTermActive(Long schoolId, Long status) {
        return this.termRepository.findBySchoolIdAndStatus(schoolId, status);
    }
}
