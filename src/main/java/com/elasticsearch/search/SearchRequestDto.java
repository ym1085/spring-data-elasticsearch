package com.elasticsearch.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto {
    private List<String> fields;
    private String searchTerm;
}
