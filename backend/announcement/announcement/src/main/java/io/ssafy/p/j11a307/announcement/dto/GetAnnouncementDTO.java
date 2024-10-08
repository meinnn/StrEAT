package io.ssafy.p.j11a307.announcement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAnnouncementDTO (
        @Schema(description = "공고 리스트")
        List<GetAnnouncementListDTO> getAnnouncementListDTOList
){
}
