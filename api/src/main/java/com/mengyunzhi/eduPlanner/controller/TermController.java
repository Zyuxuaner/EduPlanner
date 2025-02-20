package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.TermDto;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("Term")
public class TermController {
    private final static Logger logger = Logger.getLogger(TermController.class.getName());

    @Autowired
    TermService termService;

    @Autowired
    LoginService loginService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Term> add(@RequestBody Term term) {
         return this.termService.save(term);
    }

    @GetMapping("/active/{id}")
    public Response<String> active(@PathVariable Long id) {
        return this.termService.active(id);
    }

    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteTerm(@PathVariable Long id) {
        return this.termService.deleteTerm(id);
    }

    @GetMapping("/getAll")
    public List<Term> getAll() {
        return this.termService.getAll();
    }

    @GetMapping("/checkTerm")
    public Response<Optional<Term>> checkActive() {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long schoolId = currentUser.getData().getSchoolId();
        Optional<Term> term = this.termService.checkTermActive(schoolId, 1L);
        if (term.isPresent()) {
            return new Response<>(true, "存在激活学期", term);
        } else {
            return new Response<>(false, "当前学校无激活学期", term);
        }
    }

    @GetMapping("/getTermById/{id}")
    public Term getTermById(@PathVariable Long id) {
        return this.termService.getTermById(id);
    }

    @GetMapping("/getTermAndWeeks")
    public Response<TermDto.TermAndWeeksResponse> getTermAndWeeks() {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long schoolId = currentUser.getData().getSchoolId();

        return this.termService.getTermAndWeeks(schoolId);
    }

    @GetMapping("/getTermAndWeeksAndStudentsBySchoolId/{schoolId}")
    public Response<TermDto.TermAndWeeksAndStudentsResponse> getTermAndWeeksAndStudentsBySchoolId(@PathVariable Long schoolId) {
        return this.termService.getTermAndWeeksAndStudents(schoolId);
    }

    @GetMapping("/getAllSchoolIdsAndStartTime")
    public Response<List<TermDto.SchoolIdAndStartTimeResponse>> getAllSchoolIdsAndStartTime() {
        List<TermDto.SchoolIdAndStartTimeResponse> responsesData = this.termService.getSchoolIdAndStartTime();

        if (!responsesData.isEmpty()) {
            return new Response<>(true, "成功获取到所有学校的激活学期的开学日期", responsesData);
        } else {
            return new Response<>(false, "当前无学校存在激活学期", responsesData);
        }
    }

    @GetMapping("/search")
    public List<Term> searchTerms(
            @RequestParam(required = false) Long schoolId,
            @RequestParam(required = false) String searchName) {
        return termService.search(schoolId, searchName);
    }

    @PutMapping("/updateTerm/{id}")
    public Response<Term> updateTerm(@PathVariable Long id, @RequestBody Term term) {
        return this.termService.updateTerm(id, term);
    }
}
