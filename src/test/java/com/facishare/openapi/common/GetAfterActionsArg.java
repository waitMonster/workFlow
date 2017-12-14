package com.facishare.openapi.common;

import lombok.Data;

/**
 * Created by sunsk on 2017/8/4.
 */
@Data
public class GetAfterActionsArg {
    private String sourceWorkflowId = null;
    private String keyWord = null;
    private String orderBy = null;
}
