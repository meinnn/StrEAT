package io.ssafy.p.j11a307.store.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.ssafy.p.j11a307.store.dto.CreateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StoreLocationPhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreLocationPhotoService {

    private final StoreLocationPhotoRepository storeLocationPhotoRepository;
    private final StoreRepository storeRepository;
    private final AmazonS3 amazonS3;
    private final OwnerClient ownerClient;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    /**
     * StoreLocationPhoto 생성
     */
    @Transactional
    public void createStoreLocationPhoto(String token, CreateStoreLocationPhotoDTO createStoreLocationPhotoDTO) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 이미지 파일 처리 및 저장
        for (MultipartFile image : createStoreLocationPhotoDTO.images()) {
            if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
                throw new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_SRC_NULL);
            }

            String url;  // URL 변수를 try 블록 바깥에 선언

            // 이미지 업로드 및 URL 반환
            try {
                url = this.uploadImageToS3(image);  // URL 할당
            } catch (IOException e) {
                // 예외 처리 로직
                throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
            }

            // StoreLocationPhoto 엔티티 생성 및 저장
            StoreLocationPhoto storeLocationPhoto = StoreLocationPhoto.builder()
                    .store(store)  // userId로 찾은 store 객체를 사용
                    .src(url)  // URL 사용
                    .latitude(createStoreLocationPhotoDTO.latitude())
                    .longitude(createStoreLocationPhotoDTO.longitude())
                    .createdAt(LocalDateTime.now())
                    .build();

            storeLocationPhotoRepository.save(storeLocationPhoto);
        }
    }

    /**
     * StoreLocationPhoto 조회 (단건)
     */
    @Transactional(readOnly = true)
    public List<ReadStoreLocationPhotoDTO> getStoreLocationPhotosByStoreId(Integer storeId) {
        List<StoreLocationPhoto> storeLocationPhotos = storeLocationPhotoRepository.findByStoreId(storeId);
        return storeLocationPhotos.stream()
                .map(ReadStoreLocationPhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * StoreLocationPhoto 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStoreLocationPhotoDTO> getAllStoreLocationPhotos() {
        List<StoreLocationPhoto> storeLocationPhotos = storeLocationPhotoRepository.findAll();
        return storeLocationPhotos.stream()
                .map(ReadStoreLocationPhotoDTO::new)
                .collect(Collectors.toList());
    }
    /**
     * StoreLocationPhoto 수정
     */
    @Transactional
    public void updateStoreLocationPhoto(String token, Integer id, UpdateStoreLocationPhotoDTO updateDTO) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // Store의 사진을 가져옴
        StoreLocationPhoto storeLocationPhoto = storeLocationPhotoRepository.findByIdAndStoreId(id, store.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_NOT_FOUND));

        // 이미지 파일이 있을 경우 업로드 후 URL 생성
        String imageUrl = null;
        if (updateDTO.image() != null && !updateDTO.image().isEmpty()) {
            try {
                imageUrl = uploadImageToS3(updateDTO.image());  // 이미지 업로드 및 URL 반환
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);  // 파일 업로드 실패 예외 처리
            }
        }

        // DTO의 데이터를 StoreLocationPhoto에 적용 (이미지 URL 포함)
        updateDTO.applyTo(storeLocationPhoto, imageUrl);

        // 변경된 내용 저장
        storeLocationPhotoRepository.save(storeLocationPhoto);
    }

    /**
     * StoreLocationPhoto 삭제
     */
    @Transactional
    public void deleteStoreLocationPhotoByToken(String token, Integer locationPhotoId) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StoreLocationPhoto 삭제
        StoreLocationPhoto storeLocationPhoto = storeLocationPhotoRepository.findByIdAndStoreId(locationPhotoId, store.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_NOT_FOUND));

        storeLocationPhotoRepository.delete(storeLocationPhoto);
    }

    /**
     * S3에 이미지 업로드
     */
    private String uploadImageToS3(MultipartFile imageFile) throws IOException {
        // null 체크 추가
        if (imageFile.getOriginalFilename() == null) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        // 이미지 파일의 확장자 검사
        validateImageFileExtension(imageFile.getOriginalFilename());

        // 이미지 파일 S3에 업로드 및 URL 반환
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String s3Filename = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        try (InputStream is = imageFile.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + extension);
            metadata.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
            return amazonS3.getUrl(bucketName, s3Filename).toString();
        }
    }

    /**
     * 이미지 파일 확장자 검증
     */
    private void validateImageFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

        if (!allowedExtensions.contains(extension)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}
