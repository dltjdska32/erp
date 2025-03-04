package com.example.erp.domain.sendmail.dto.req;

import java.util.List;

public record DeleteSendMailReqDto(
        List<Long> ids
) {
}
