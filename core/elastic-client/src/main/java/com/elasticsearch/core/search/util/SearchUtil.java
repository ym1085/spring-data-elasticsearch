package com.elasticsearch.core.search.util;

import com.elasticsearch.core.search.SearchRequestDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class SearchUtil {

    /**
     * MatchAll Query
     *
     * @param indexName
     * @return
     */
    public static SearchRequest buildSearchRequestByMatchAllQuery(String indexName) {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .size(50)
                .query(getQueryBuilder());
        return new SearchRequest(indexName).source(builder);
    }

    /**
     * Match || Multi Match Query 기반, SearchRequest 객체 생성 후 반환
     *
     * @param indexName
     * @param searchRequestDto
     * @return
     */
    public static SearchRequest buildSearchRequestByMatchQuery(String indexName, SearchRequestDto searchRequestDto) {
        SearchRequest request = null;
        try {
            int page = searchRequestDto.getPage();
            int size = searchRequestDto.getSize();
            int from = page <= 0 ? 0 : page * size;

            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(searchRequestDto));

            if (searchRequestDto.getSortBy() != null) {
                builder.sort(
                        searchRequestDto.getSortBy(),
                        searchRequestDto.getOrder() != null ? searchRequestDto.getOrder() : SortOrder.ASC
                );
            }
            request = new SearchRequest(indexName);
            request.source(builder);
        } catch (Exception e) {
            e.printStackTrace();
            request = null;
        }
        return request;
    }

    /**
     * Range Query 기반, SearchRequest 객체 생성 후 반환
     *
     * @param indexName : [String] 검색 대상이 되는 인덱스명
     * @param field     : [String] 검색을 원하는 필드명
     * @param date      : [Date] 검색 조건 날짜(gte)
     * @return          : [SearchRequest]
     */
    public static SearchRequest buildSearchRequestByRangeQuery(String indexName, String field, Date date) {
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Bool Query 기반, SearchRequest 객체 생성 후 반환
     *
     * @param indexName         : [String] 검색 대상이 되는 인덱스명
     * @param searchRequestDto  : ""
     * @param date              : [Date] 검색 조건 날짜(gte)
     * @return                  : [SearchRequest]
     */
    public static SearchRequest buildSearchRequestByBoolQuery(String indexName, SearchRequestDto searchRequestDto, Date date) {
        SearchRequest request = null;
        try {
            QueryBuilder searchQueryBuilder = getQueryBuilder(searchRequestDto);
            QueryBuilder dateQueryBuilder = getQueryBuilder("created_at", date);

            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(searchQueryBuilder)
                .must(dateQueryBuilder);

            SearchSourceBuilder builder = new SearchSourceBuilder()
                .postFilter(boolQuery);

            if (searchRequestDto.getSortBy() != null) {
                builder.sort(
                    searchRequestDto.getSortBy(), // condition field
                    searchRequestDto.getOrder() != null ? searchRequestDto.getOrder() : SortOrder.ASC
                );
            }

            request = new SearchRequest(indexName);
            request.source(builder);
        } catch (Exception e) {
            e.printStackTrace();
            request = null;
        }
        return request;
    }

    private static QueryBuilder getQueryBuilder() {
        return QueryBuilders.matchAllQuery();
    }

    private static QueryBuilder getQueryBuilder(SearchRequestDto searchRequestDto) {
        if (ObjectUtils.isEmpty(searchRequestDto)) {
            return null;
        }

        List<String> fields = searchRequestDto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if (fields.size() > 1) { // search multi field
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchRequestDto.getSearchTerm())
                        .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                        .operator(Operator.AND);

            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field ->
                    QueryBuilders.matchQuery(field, searchRequestDto.getSearchTerm()) // search single field
                            .operator(Operator.AND))
                .orElse(null);
    }

    private static QueryBuilder getQueryBuilder(String field, Date date) {
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
