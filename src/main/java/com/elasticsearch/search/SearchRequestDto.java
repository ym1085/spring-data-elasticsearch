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
    private List<String> fields; // standard to search
    private String searchTerm; // search keyword
    private String sortBy; // standard to sort
    private SortOrder order; // asc, desc
}
