package com.elasticsearch.search;

import lombok.*;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchRequestDto extends PagedRequestDTO {

    private List<String> fields;    // 검색을 원하는 필드명
    private String searchTerm;      // 검색 단어(Term 단위)
    private String sortBy;          // 정렬 기준 필드, ex) created_at
    private SortOrder order;        // 정렬 기준, ex) DESC, ASC

}
