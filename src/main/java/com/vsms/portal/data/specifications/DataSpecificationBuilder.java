package com.vsms.portal.data.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vsms.portal.data.SearchCriteria;

import com.vsms.portal.exception.ApiOperationException;
import org.springframework.data.jpa.domain.Specification;

public class DataSpecificationBuilder<T> {
    private final List<SearchCriteria<T>> params;

    public DataSpecificationBuilder() {
        params = new ArrayList<SearchCriteria<T>>();
    }

    public DataSpecificationBuilder<T> with(String key, String operation, Object value, Class<T> clazz) throws Exception {
        params.add(new SearchCriteria<T>(key, operation, value, clazz));
        return this;
    }

    public Specification<T> build() {
        if (params.size() != 0) {
            List<Specification<T>> specs = params.stream().map(DataSpecification::new).collect(Collectors.toList());

            Specification result = specs.get(0);

            for (int i = 1; i < params.size(); i++) {
                result = params.get(i).isOrPredicate() ? Specification.where(result).or(specs.get(i))
                        : Specification.where(result).and(specs.get(i));
            }
            return result;
        } else {
            return null;
        }
    }
}