package com.example.erp.domain.board.dto.req;

import java.util.List;

public record BoardDeleteReqDto (
        List<Long> ids
) {

}
