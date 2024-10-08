package io.ssafy.p.j11a307.announcement.repository;

import io.ssafy.p.j11a307.announcement.entity.RecruitAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<RecruitAnnouncement, Integer> {
}
