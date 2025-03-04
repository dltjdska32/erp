package com.example.erp.domain.receivedmail.dto.req;

import java.util.List;

public record DeleteReceivedMailReqDto(
        List<Long> ids
) {
}
