package io.ssafy.p.j11a307.announcement.service;

import io.ssafy.p.j11a307.announcement.dto.GetAnnouncementDTO;
import io.ssafy.p.j11a307.announcement.dto.GetAnnouncementListDTO;
import io.ssafy.p.j11a307.announcement.dto.GetSubmitFileRequest;
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

//        String ownerName = getSubmitFileRequest.ownerName();
//        String gender = getSubmitFileRequest.gender();
//        Integer age = getSubmitFileRequest.age();
//        String birth = getSubmitFileRequest.birth();
//        String address = getSubmitFileRequest.address();
//        String home_num = getSubmitFileRequest.home_num();
//        String phone_num = getSubmitFileRequest.phone_num();
//        String email = getSubmitFileRequest.email();
//        String sns = getSubmitFileRequest.sns();
//        String truckName = getSubmitFileRequest.truckName();
//        String businessNum = getSubmitFileRequest.businessNum();
          String eventName = getSubmitFileRequest.eventName();

        try {
            //System.out.println("현재 작업 디렉토리: " + System.getProperty("user.dir"));

            //String currentDir = System.getProperty("user.dir");
            //System.out.println("현재 작업 디렉토리: " + currentDir);

            // 현재 작업 디렉토리의 파일 목록 가져오기
//            File dir = new File(currentDir);
//            File[] filesList = dir.listFiles();
//
//            if (filesList != null) {
//                System.out.println("디렉토리 내 파일 목록:");
//                for (File file : filesList) {
//                    System.out.println(file.getName());
//                }
//            } else {
//                System.out.println("디렉토리가 비어있거나 접근할 수 없습니다.");
//            }

            // Python에서 생성한 파일 경로

            Path filePath2 = Paths.get("truck.docx"); // Python에서 생성한 파일 경로를 지정
            File file2 = filePath2.toFile();

             //파일이 존재하지 않으면 오류 메시지 반환
            if (!file2.exists()) throw new BusinessException(ErrorCode.FILE_NOT_FOUND);

            //System.out.println("파일 존재함!!");

//            ProcessBuilder pb = new ProcessBuilder("python", "var/jenkins_home/workspace/streat-docker-pipeline-announcement/backend/announcement/announcement/src/main/java/io/ssafy/p/j11a307/announcement/submitWordFile.py",
//                    ownerName,gender,Integer.toString(age), birth, address, home_num, phone_num, email, sns, truckName, businessNum, eventName);
            ProcessBuilder pb = new ProcessBuilder("python", "submitWordFile.py");

            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Python의 표준 출력을 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            //출력 내용 출력
            System.out.println("Python 출력: " + output.toString());

            // Python에서 생성한 파일 경로
            Path filePath = Paths.get("newfile.docx"); // Python에서 생성한 파일 경로를 지정
            File file = filePath.toFile();

            // 파일이 존재하지 않으면 오류 메시지 반환
            if (!file.exists()) throw new BusinessException(ErrorCode.FILE_NOT_FOUND);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=newfile.docx");

            FileSystemResource fileSystemResource = new FileSystemResource(file);

            return fileSystemResource;
        } catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SUBMIT_FILE_EXCEPTION);
        }

    }
}
