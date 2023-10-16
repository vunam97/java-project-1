package com.vti.service;

import com.vti.dto.DepartmentDto;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.DepartmentCreateForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.DepartmentUpdateForm;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.DepartmentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    private final IDepartmentRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public DepartmentService(IDepartmentRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<DepartmentDto> findAll(DepartmentFilterForm form, Pageable pageable) {
        Specification<Department> spec = DepartmentSpecification.buildSpec(form);
        return repository.findAll(spec, pageable)
                .map(department -> mapper.map(department, DepartmentDto.class).withSelfRel());
    }

    @Override
    public DepartmentDto findById(Integer id) {
        return repository.findById(id)
                .map(department -> mapper.map(department, DepartmentDto.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public void create(DepartmentCreateForm form) {
        Department department = mapper.map(form, Department.class);
        List<Account> accounts = department.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                account.setDepartment(department);
            }
        }
        repository.save(department);
    }

    @Override
    public void update(Integer id, DepartmentUpdateForm form) {
        Department department = mapper.map(form, Department.class);
        department.setId(id);
        repository.save(department);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        repository.deleteAllById(ids);
    }
}
