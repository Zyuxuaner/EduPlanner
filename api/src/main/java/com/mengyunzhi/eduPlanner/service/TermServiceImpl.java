package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.School;
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

    private final TermRepository termRepository;

    @Autowired
    private TermServiceImpl(TermRepository termRepository) {
       this.termRepository = termRepository;
    }

    /**
     * 查询传入的 termId 对应的学期
     * 获取该学校所属的学校
     * 查询该学校下的所有学期，确保只有一个学期是激活状态
     * 激活当前传入的学期
     */
    @Override
    public Response<String> active(Long termId) {
        Long ACTIVE_STATUS = 1L;
        Optional<Term> termOptional = termRepository.findById(termId);
        if (!termOptional.isPresent()) {
            return Response.fail("学期不存在");
        }
        Term term = termOptional.get();

        School school = term.getSchool();
        if (school == null) {
            return Response.fail("学期所属学校不存在");
        }

        List<Term> allTerms = termRepository.findBySchoolId(school.getId());
        for (Term t : allTerms) {
            if (t.getStatus().equals(ACTIVE_STATUS) && !t.getId().equals(termId)) {
                Long FREEZE_STATUS = 0L;
                t.setStatus(FREEZE_STATUS);
                termRepository.save(t);
            }
        }

        term.setStatus(ACTIVE_STATUS);
        termRepository.save(term);

        return Response.success("学期激活成功");
    }

    @Override
    public Response<Term> save(Term term) {
        // 新增学期，状态默认为冻结（status = 0）
        term.setStatus(0L);
        termRepository.save(term);
        return new Response<>(true,"新增成功",null);
    }

    @Override
    public Response<Void> deleteTerm(Long id) {
        termRepository.deleteById(id);
        return new Response<>(true,"删除成功",null);
    }

    @Override
    public List<Term> getAll() {
        return this.termRepository.findAll();
    }

    @Override
    public Term getTermById(Long id) {
        return this.termRepository.findById(id).orElse(null);
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

    @Override
    public List<TermDto.SchoolIdAndStartTimeResponse> getSchoolIdAndStartTime () {
        List<TermDto.SchoolIdAndStartTimeResponse> responseList = new ArrayList<>();

        // 定义激活学期的状态值
        Long ACTIVE_STATUS = 1L;

        List<Term> activeTerms = termRepository.findAllByStatus(ACTIVE_STATUS);

        // 遍历查询结果，构建返回数据
        for (Term term : activeTerms) {
            TermDto.SchoolIdAndStartTimeResponse response = new TermDto.SchoolIdAndStartTimeResponse();
            response.setSchoolId(term.getSchool().getId());
            response.setName(term.getSchool().getName());
            response.setStartTime(term.getStartTime());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public Response<Term> updateTerm (Long id, Term term) {
        // 根据 ID 查找学期
        Optional<Term> optionalTerm = termRepository.findById(id);

        if (optionalTerm.isPresent()) {
            Term existingTerm = optionalTerm.get();

            // 更新学期信息
            if (term.getName() != null) {
                existingTerm.setName(term.getName());
            }
            if (term.getStartTime() != null) {
                existingTerm.setStartTime(term.getStartTime());
            }
            if (term.getEndTime() != null) {
                existingTerm.setEndTime(term.getEndTime());
            }
            if (term.getSchool() != null) {
                existingTerm.setSchool(term.getSchool());
            }

            // 保存更新后的学期信息
            Term updatedTerm = termRepository.save(existingTerm);

            return new Response<>(true, "学期信息更新成功", updatedTerm);
        } else {
            return new Response<>(false, "未找到指定 ID 的学期信息", null);
        }
    }
}
