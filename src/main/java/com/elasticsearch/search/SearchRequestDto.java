package com.elasticsearch.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto {
    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;
}
