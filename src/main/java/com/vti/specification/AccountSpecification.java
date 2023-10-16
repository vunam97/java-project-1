package com.vti.specification;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AccountSpecification {
    public static Specification<Account> buildSpec(AccountFilterForm form) {
       return (root, query, builder) -> {
           if (form == null) {
               return null;
           }
           List<Predicate> predicates = new ArrayList<>();
           if (StringUtils.hasText(form.getSearch())) {
               String pattern = "%" + form.getSearch().trim() + "%";
               Path<String> username = root.get("username");
               Predicate hasUsernameLike = builder.like(username, pattern); // username LIKE "%search%"
               Path<String> departmentName = root.get("department").get("name");
               Predicate hasDepartmentNameLike = builder.like(departmentName, pattern);
               predicates.add(builder.or(hasUsernameLike, hasDepartmentNameLike)); // departmentName LIKE "%search%"
           }
           if (form.getMinId() != null) {
               Path<Integer> id = root.get("id");
               Predicate predicate = builder.greaterThanOrEqualTo(id, form.getMinId());
               predicates.add(predicate);
           }
           if (form.getMaxId() != null) {
               Path<Integer> id = root.get("id");
               Predicate predicate = builder.lessThanOrEqualTo(id, form.getMaxId());
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
