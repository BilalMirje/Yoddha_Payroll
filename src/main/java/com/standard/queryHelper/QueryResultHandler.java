package com.standard.queryHelper;


import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.EmptyListException;
import com.standard.exceptions.handlers.EmptyPageException;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class QueryResultHandler {
    private final PageableResolverUtil pageableResolverUtil;
    private final QueryExecutionHelper queryExecutionHelper;
    public <E, D> ApiResponse<?> fetchAndRespond(
         PageRequest pageRequest,
            Function<Pageable, Page<E>> pageCall,
            Function<Sort, List<E>> sortCall,
            Supplier<List<E>> listCall,
            Function<E, D> mapper,
            String message
    ) {

        if (pageableResolverUtil.hasPage(pageRequest)) {

            Page<D> page = pageCall
                    .apply(pageableResolverUtil.resolve(pageRequest))
                    .map(mapper);

            if (page.isEmpty()) {
                throw new EmptyPageException(message);            }

            return ApiResponseUtil.fetchedPage(page);
        }

        List<D> list = queryExecutionHelper.fetchList(pageRequest, sortCall, listCall)
                .stream()
                .map(mapper)
                .toList();

        if (list.isEmpty()) {
            throw new EmptyListException(message);
        }

        return ApiResponseUtil.fetchedList(list);
    }

}
