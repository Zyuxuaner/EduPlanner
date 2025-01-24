package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        // 新增学期，状态默认为冻结（status = 0）
        term.setStatus(0L);
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

    /**
     * 根据 schoolId 来获取激活学期
     * 存在激活学期，则计算周数，拼接 Response<TermDto.TermAndWeeksResponse> 类型返回值
     * 反之，返回空列表
     * @param schoolId 学校 id
     * @return
     */
    @Override
    public Response<TermDto.TermAndWeeksResponse> getTermAndWeeks(Long schoolId) {
        Optional<Term> termOpt = this.termRepository.findBySchoolIdAndStatus(schoolId, 1L);

        if (termOpt.isPresent()) {
            Term term = termOpt.get();
            Timestamp startTime = term.getStartTime();
            Timestamp endTime = term.getEndTime();

            LocalDateTime startDateTime = startTime.toLocalDateTime();
            LocalDateTime endDateTime = endTime.toLocalDateTime();

            int startWeek = 1;
            List<Integer> weeks = new ArrayList<>();
            long totalWeeks = ChronoUnit.WEEKS.between(startDateTime, endDateTime);

            for (int i = startWeek; i <= totalWeeks; i++) {
                weeks.add(i);
            }

            TermDto.TermAndWeeksResponse responseData = new TermDto.TermAndWeeksResponse();
            responseData.setTerm(term);
            responseData.setWeeks(weeks);

            return new Response<>(true, "成功获取学期即周数", responseData);
        } else {
            return new Response<>(false, "当前无激活学期", null);
        }
    }
}
