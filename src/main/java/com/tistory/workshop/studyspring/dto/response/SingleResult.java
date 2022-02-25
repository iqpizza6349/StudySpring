package com.tistory.workshop.studyspring.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SingleResult<T> extends CommonResult {
    
    private T data;
}
