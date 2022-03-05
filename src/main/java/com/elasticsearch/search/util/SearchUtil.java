package com.elasticsearch.search.util;

import com.elasticsearch.search.SearchRequestDto;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class SearchUtil {

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDto searchRequestDto) {
        SearchRequest request = null;
        try {
            final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(searchRequestDto));

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

    public static SearchRequest buildSearchRequest(final String indexName, final String field, final Date date) {
        try {
            final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDto searchRequestDto, final Date date) {
        SearchRequest request = null;
        try {
            final QueryBuilder searchQueryBuilder = getQueryBuilder(searchRequestDto);
            final QueryBuilder dateQueryBuilder = getQueryBuilder("created_at", date);

            final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
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

    private static QueryBuilder getQueryBuilder(final SearchRequestDto searchRequestDto) {
        if (ObjectUtils.isEmpty(searchRequestDto)) {
            return null;
        }

        List<String> fields = searchRequestDto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if (fields.size() > 1) {
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchRequestDto.getSearchTerm()) // search multi field
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);

            /*fields.forEach((n) -> {
                queryBuilder.field(n)
            });*/

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

    private static QueryBuilder getQueryBuilder(final String field, final Date date) {
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
