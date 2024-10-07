package io.ssafy.p.j11a307.announcement.service;

import io.ssafy.p.j11a307.announcement.dto.GetAnnouncementDTO;
import io.ssafy.p.j11a307.announcement.dto.GetAnnouncementListDTO;
import io.ssafy.p.j11a307.announcement.entity.RecruitAnnouncement;
import io.ssafy.p.j11a307.announcement.exception.BusinessException;
import io.ssafy.p.j11a307.announcement.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.ssafy.p.j11a307.announcement.repository.AnnouncementRepository;

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
}
