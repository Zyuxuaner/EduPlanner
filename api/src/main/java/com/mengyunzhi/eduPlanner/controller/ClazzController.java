package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.service.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("Clazz")
public class ClazzController {
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    ClazzService clazzService;

    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteClazz(@PathVariable Long id) {
        return this.clazzService.deleteClazz(id);
    }

    @GetMapping("/getAll")
    public List<Clazz> getAll() {
        return this.clazzService.getAll();
    }

    @GetMapping("/getClazzBySchoolId/{schoolId}")
    public List<Clazz> getClazzBySchoolId(@PathVariable Long schoolId) {
        return this.clazzService.getClazzBySchoolId(schoolId);
    }

    @GetMapping("/{clazzId}")
    public Clazz getClazzByClazzId(@PathVariable Long clazzId) {
        return this.clazzService.getClazzByClazzId(clazzId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> save(@RequestBody Clazz clazz) {
       return clazzService.save(clazz);
    }

    @GetMapping("/search")
    public List<Clazz> searchClazzes(@RequestParam(required = false) Long schoolId, @RequestParam(required = false) String clazzName) {
        return clazzService.searchClazzes(schoolId, clazzName);
    }

    @PutMapping("update/{clazzId}")
    public Response<Clazz> updateClazz(@PathVariable Long clazzId, @RequestBody Map<String, Object> requestBody) {
        Long schoolId = Long.valueOf(requestBody.get("schoolId").toString());
        String name = requestBody.get("name").toString();
        return clazzService.updateClazz(clazzId, schoolId, name);
    }
}
