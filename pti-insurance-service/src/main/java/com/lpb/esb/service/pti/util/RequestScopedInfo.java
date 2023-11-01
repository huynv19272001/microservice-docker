package com.lpb.esb.service.pti.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class RequestScopedInfo {
    private String msgId;
}
