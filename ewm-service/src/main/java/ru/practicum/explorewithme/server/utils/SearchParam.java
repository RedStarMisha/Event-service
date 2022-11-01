package ru.practicum.explorewithme.server.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Getter
public class SearchParam {
    private BooleanExpression booleanExpression;
    private Pageable pageable;
}
