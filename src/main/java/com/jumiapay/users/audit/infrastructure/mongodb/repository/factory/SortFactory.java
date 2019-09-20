package com.jumiapay.users.audit.infrastructure.mongodb.repository.factory;

import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortFactory {

    public Sort getSort(String[] sort, DirectionEnum direction) {
        Sort sorting = Sort.by(sort);

        switch (direction){
            case ASC: return sorting.ascending();
            case DESC: return sorting.descending();
        }

        return sorting;
    }

}
