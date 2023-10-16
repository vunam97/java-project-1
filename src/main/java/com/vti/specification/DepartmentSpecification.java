package com.vti.specification;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {
    public static Specification<Department> buildSpec(DepartmentFilterForm form) {
        return (form == null) ? null : (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(form.getSearch())) {
                String pattern = "%" + form.getSearch().trim() + "%";
                Path<String> name = root.get("name");
                Predicate hasNameLike = builder.like(name, pattern);
                Path<String> accountUsername = root.join("accounts", JoinType.LEFT).get("username");
                Predicate hasAccountUsernameLike = builder.like(accountUsername, pattern);
                predicates.add(builder.or(hasNameLike, hasAccountUsernameLike));
            }
            if (form.getType() != null) {
                Path<String> type = root.get("type");
                Predicate predicate = builder.equal(type, form.getType());
                predicates.add(predicate);
            }
            if (form.getMinTotalMembers() != null) {
                Path<Integer> totalMembers = root.get("totalMembers");
                Predicate predicate = builder.greaterThanOrEqualTo(totalMembers, form.getMinTotalMembers());
                predicates.add(predicate);
            }
            if (form.getMaxTotalMembers() != null) {
                Path<Integer> totalMembers = root.get("totalMembers");
                Predicate predicate = builder.lessThanOrEqualTo(totalMembers, form.getMaxTotalMembers());
                predicates.add(predicate);
            }
            if (form.getMinCreatedDate() != null) {
                Path<LocalDateTime> createdAt = root.get("created_at");
                LocalDateTime input = form.getMinCreatedDate().atStartOfDay();
                Predicate predicate = builder.greaterThanOrEqualTo(createdAt, input);
                predicates.add(predicate);
            }
            if (form.getMaxCreatedDate() != null) {
                Path<LocalDateTime> createdAt = root.get("created_at");
                LocalDateTime input = form.getMaxCreatedDate().atStartOfDay();
                Predicate predicate = builder.lessThanOrEqualTo(createdAt, input);
                predicates.add(predicate);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
