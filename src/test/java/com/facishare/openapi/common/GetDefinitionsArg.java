package com.facishare.openapi.common;

import lombok.Data;

/**
 * Created by sunsk on 2017/8/4.
 */
@Data
public class GetDefinitionsArg {
    private String entityId = null;
    private String keyWord = null;
    private String enable = null;
}
