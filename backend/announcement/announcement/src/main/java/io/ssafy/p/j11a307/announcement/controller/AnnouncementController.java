package io.ssafy.p.j11a307.announcement.controller;

import io.ssafy.p.j11a307.announcement.dto.GetAnnouncementDTO;
import io.ssafy.p.j11a307.announcement.global.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.ssafy.p.j11a307.announcement.service.AnnouncementService;
import io.ssafy.p.j11a307.announcement.dto.GetSubmitFileRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping("/list")
    @Operation(summary = "공고 내역 조회", description = "푸드트럭 협회 공고 내용을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공고 내역 조회 성공"),
    })
    public ResponseEntity<DataResponse<GetAnnouncementDTO>> getStoreOrderList(@RequestHeader("Authorization") String token) {
        GetAnnouncementDTO getAnnouncementDTO = announcementService.getStoreOrderList(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("공고 내역 조회에 성공했습니다.", getAnnouncementDTO));
    }


    @PostMapping("/create-doc")
    @Operation(summary = "제출 파일 자동화", description = "제출할 파일 내용 입력을 자동화한 후 파일을 돌려준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자동화 성공")
    })
    public FileSystemResource GetSubmitFile(@RequestHeader("Authorization") String token,
                                                                          @RequestBody GetSubmitFileRequest getSubmitFileRequest) {
        FileSystemResource fileSystemResource = announcementService.getSubmitFile(token, getSubmitFileRequest);

        return fileSystemResource;
    }
}