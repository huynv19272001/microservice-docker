package com.lpd.esb.service.mobifone.util;

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
