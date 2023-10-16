package com.vti.service;

import com.vti.dto.DepartmentDto;
import com.vti.form.DepartmentCreateForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.DepartmentUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDepartmentService {
    Page<DepartmentDto> findAll(DepartmentFilterForm form, Pageable pageable);

    DepartmentDto findById(Integer id);

    void create(DepartmentCreateForm form);

    void update(Integer id, DepartmentUpdateForm form);

    void deleteById(Integer id);

    void deleteAllById(List<Integer> ids);
}
