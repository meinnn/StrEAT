package io.ssafy.p.j11a307.announcement.service;

import io.ssafy.p.j11a307.announcement.dto.*;
import io.ssafy.p.j11a307.announcement.entity.RecruitAnnouncement;
import io.ssafy.p.j11a307.announcement.exception.BusinessException;
import io.ssafy.p.j11a307.announcement.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import io.ssafy.p.j11a307.announcement.repository.AnnouncementRepository;

import java.io.*;
import java.io.File;
import org.springframework.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final OwnerClient ownerClient;
    private final StoreClient storeClient;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public GetAnnouncementDTO getStoreOrderList(String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);

        if(ownerId == null) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        List<RecruitAnnouncement> announcementList = announcementRepository.findAll();
        List<GetAnnouncementListDTO> announceList = new ArrayList<>();

        for (RecruitAnnouncement announcement : announcementList) {

            GetAnnouncementListDTO getAnnouncementListDTO = GetAnnouncementListDTO.builder()
                    .id(announcement.getId())
                    .entryConditions(announcement.getEntryConditions())
                    .recruitSize(announcement.getRecruitSize())
                    .eventDays(announcement.getEventDays())
                    .eventName(announcement.getEventName())
                    .eventPlace(announcement.getEventPlace())
                    .eventTimes(announcement.getEventTimes())
                    .recruitPostTitle(announcement.getRecruitPostTitle())
                    .specialNotes(announcement.getSpecialNotes())
                    .build();

            announceList.add(getAnnouncementListDTO);
        }

        GetAnnouncementDTO announcementDTO = GetAnnouncementDTO.builder()
                .getAnnouncementListDTOList(announceList)
                .build();

        return announcementDTO;
    }

    @Transactional
    public FileSystemResource getSubmitFile(String token, GetSubmitFileRequest getSubmitFileRequest) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);

        OwnerProfile ownerProfile = ownerClient.getAnnouncementOwnerInformation(internalRequestKey, ownerId).getData();

        String ownerName = ownerProfile.ownerName();
        String gender = ownerProfile.gender();
        Integer age = ownerProfile.age();
        String birth = ownerProfile.birth();
        String address = ownerProfile.address();
        String home_num = ownerProfile.tel();
        String phone_num = ownerProfile.phone();
        String email = ownerProfile.email();
        String sns = ownerProfile.snsAccount();

        Integer storeId = storeClient.getStoreIdByUserId(ownerId);
        ReadStoreDTO readStoreDTO = storeClient.getStoreInfo(storeId).getData();

        String truckName = readStoreDTO.name();
        String businessNum = readStoreDTO.businessRegistrationNumber();
        String eventName = getSubmitFileRequest.eventName();

        try {
            // Python에서 생성한 파일 경로
            Path filePath2 = Paths.get("truck.docx"); // Python에서 생성한 파일 경로를 지정
            File file2 = filePath2.toFile();
//
//             //파일이 존재하지 않으면 오류 메시지 반환
            if (!file2.exists()) throw new BusinessException(ErrorCode.FILE_NOT_FOUND);

            ProcessBuilder pb2 = new ProcessBuilder("pip3", "install", "python-docx");
            pb2.redirectErrorStream(true);
            Process process2 = pb2.start();
            process2.waitFor();

            //ProcessBuilder pb = new ProcessBuilder("python3", "submitWordFile.py");
            ProcessBuilder pb = new ProcessBuilder("python3", "submitWordFile.py",
                    ownerName,gender,Integer.toString(age), birth, address, home_num, phone_num, email, sns, truckName, businessNum, eventName);

            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

//            // Python의 표준 출력을 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            //출력 내용 출력
            System.out.println("Python 출력: " + output.toString());

            // Python에서 생성한 파일 경로
            Path filePath = Paths.get("입점신청서.docx"); // Python에서 생성한 파일 경로를 지정
            File file = filePath.toFile();

            // 파일이 존재하지 않으면 오류 메시지 반환
            if (!file.exists()) throw new BusinessException(ErrorCode.FILE_NOT_FOUND);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=입점신청서.docx");

            FileSystemResource fileSystemResource = new FileSystemResource(file);

            return fileSystemResource;
        } catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SUBMIT_FILE_EXCEPTION);
        }

    }
}
