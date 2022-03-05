package com.elasticsearch.service;

import com.elasticsearch.document.User;
import com.elasticsearch.dto.request.UserRequestDto;

public interface UserService {

    Boolean saveDocumentToIndex(UserRequestDto userRequestDto);

    User findById(UserRequestDto userRequestDto);

}
