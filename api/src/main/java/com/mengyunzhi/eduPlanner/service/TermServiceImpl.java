package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.CourseRepository;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    private TermServiceImpl(TermRepository termRepository,
                            StudentRepository studentRepository,
                            CourseRepository courseRepository) {
        this.termRepository = termRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
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

        return Response.success(null,"学期激活成功");
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
        // 检查学期是否存在
        if (!termRepository.existsById(id)) {
            return new Response<>(false, "学期不存在", null);
        }

        // 检查该学期下是否有课程
        if (courseRepository.existsByTermId(id)) {
            return new Response<>(false, "该学期下有课程，无法删除", null);
        }

        // 如果没有课程，删除学期
        termRepository.deleteById(id);
        return new Response<>(true, "删除成功", null);
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
    public List<Term> search(Long schoolId, String searchName) {
        Specification<Term> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (schoolId != null) {
                predicates.add(criteriaBuilder.equal(root.get("school").get("id"), schoolId));
            }

            if (searchName != null && !searchName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchName + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 使用构建好的 Specification 进行查询
        return termRepository.findAll(spec);
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
    @Override
    public Response<TermDto.TermAndWeeksAndStudentsResponse> getTermAndWeeksAndStudents(Long schoolId) {
        Response<TermDto.TermAndWeeksResponse> termAndWeeksResponse = getTermAndWeeks(schoolId);

        if (!termAndWeeksResponse.isStatus()) {
            // 如果获取学期和周数失败，直接返回错误信息
            return new Response<>(false, termAndWeeksResponse.getMessage(), null);
        }

        List<Student> students = studentRepository.findBySchoolId(schoolId);

        TermDto.TermAndWeeksAndStudentsResponse responseData = new TermDto.TermAndWeeksAndStudentsResponse();
        responseData.setTerm(termAndWeeksResponse.getData().getTerm());
        responseData.setWeeks(termAndWeeksResponse.getData().getWeeks());

        if (students == null || students.isEmpty()) {
            responseData.setStudents(null);
            return new Response<>(true, "成功获取学期、周数", responseData);
        } else {
            responseData.setStudents(students);
            return new Response<>(true, "成功获取学期、周数及学生信息", responseData);
        }
    }

    @Override
    public Long getTotalWeeksByTerm(Term term) {
        Timestamp startTime = term.getStartTime();
        Timestamp endTime = term.getEndTime();

        LocalDateTime startDateTime = startTime.toLocalDateTime();
        LocalDateTime endDateTime = endTime.toLocalDateTime();

        return ChronoUnit.WEEKS.between(startDateTime, endDateTime);
    }
}
