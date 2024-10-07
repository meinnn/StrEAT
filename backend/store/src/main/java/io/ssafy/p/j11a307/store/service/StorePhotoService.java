package io.ssafy.p.j11a307.store.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.ssafy.p.j11a307.store.dto.CreateStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStorePhotoDTO;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StorePhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorePhotoService {

    private final StorePhotoRepository storePhotoRepository;
    private final StoreRepository storeRepository;
    private final AmazonS3 amazonS3;
    private final OwnerClient ownerClient;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${streat.internal-request}")
    private String internalRequestKey;


    /**
     * StorePhoto 생성
     */
    @Transactional
    public void createStorePhotos(String token, List<MultipartFile> imageFiles) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 이미지 파일 리스트 처리
        for (MultipartFile imageFile : imageFiles) {
            validateImageFile(imageFile);  // 이미지 파일 검증
            String imageUrl;
            try {
                imageUrl = uploadImageToS3(imageFile);  // 이미지 S3에 업로드 후 URL 반환
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);  // 파일 업로드 실패 예외 처리
            }

            // StorePhoto 엔티티 생성 및 저장
            StorePhoto storePhoto = StorePhoto.builder()
                    .store(store)  // 조회한 store 객체 사용
                    .src(imageUrl)  // S3 이미지 경로 설정
                    .createdAt(LocalDateTime.now())  // 현재 시간 설정
                    .build();

            storePhotoRepository.save(storePhoto);
        }
    }

    /**
     * 해당 가게의 StorePhoto 전체 조회
     */
    public List<ReadStorePhotoDTO> getStorePhotosByStoreId(Integer storeId) {
        List<StorePhoto> storePhotos = storePhotoRepository.findByStoreId(storeId);
        return storePhotos.stream()
                .map(ReadStorePhotoDTO::new)
                .collect(Collectors.toList());
    }
    /**
     * StorePhoto 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStorePhotoDTO> getAllStorePhotos() {
        List<StorePhoto> storePhotos = storePhotoRepository.findAll();
        return storePhotos.stream()
                .map(ReadStorePhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * S3에 이미지 업로드
     */
    private String uploadImageToS3(MultipartFile imageFile) throws IOException {
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


    /**
     * StorePhoto 수정
     */
    @Transactional
    public void updateStorePhoto(String token, Integer storePhotoId, MultipartFile image) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // storePhotoId로 storePhoto 조회
        StorePhoto existingStorePhoto = storePhotoRepository.findById(storePhotoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_PHOTO_NOT_FOUND));

        Store store = existingStorePhoto.getStore();  // store를 기존 StorePhoto에서 가져옴

        // 기존 이미지 삭제
        storePhotoRepository.delete(existingStorePhoto);

        // 이미지 파일 검증 및 S3 업로드
        validateImageFile(image);
        String imageUrl;
        try {
            imageUrl = uploadImageToS3(image);  // 이미지 S3에 업로드 후 URL 반환
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);  // 파일 업로드 실패 예외 처리
        }

        // 새로운 StorePhoto 엔티티 생성 및 저장
        StorePhoto newStorePhoto = StorePhoto.builder()
                .store(store)  // 기존 StorePhoto의 store 사용
                .src(imageUrl)  // 업로드된 이미지 URL 설정
                .createdAt(LocalDateTime.now())  // 현재 시간 설정
                .build();

        storePhotoRepository.save(newStorePhoto);
    }
    /**
     * StorePhoto 삭제 (token으로 storeId 조회 후 삭제)
     */
    @Transactional
    public void deleteStorePhotoByToken(String token, Integer photoId) {
        // 토큰으로 사용자 ID 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StorePhoto 조회
        StorePhoto storePhoto = storePhotoRepository.findById(photoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_PHOTO_NOT_FOUND));

        // StorePhoto 삭제
        storePhotoRepository.delete(storePhoto);
    }

    /**
     * token을 사용해 storeId를 조회하는 메서드
     */
    public Integer getStoreIdByToken(String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER);
        }

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        return store.getId();
    }

    /**
     * 이미지 파일 검증
     */
    private void validateImageFile(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.getOriginalFilename() == null) {
            throw new BusinessException(ErrorCode.STORE_PHOTO_NULL);
        }

        int lastDotIndex = imageFile.getOriginalFilename().lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        String extension = imageFile.getOriginalFilename().substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

        if (!allowedExtensions.contains(extension)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}
