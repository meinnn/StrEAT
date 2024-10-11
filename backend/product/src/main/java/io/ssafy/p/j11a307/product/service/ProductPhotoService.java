package io.ssafy.p.j11a307.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.ssafy.p.j11a307.product.dto.ReadProductPhotoDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductPhotoRepository;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
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
import io.ssafy.p.j11a307.product.global.S3Service;

@Service
@RequiredArgsConstructor
public class ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;
    private final ProductRepository productRepository;
    private final AmazonS3 amazonS3;
    private final OwnerClient ownerClient;
    private final StoreClient storeClient;
    private final S3Service s3Service;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    /**
     * ProductPhoto 생성 (복수 이미지 업로드)
     */
    @Transactional
    public void createProductPhoto(String token, Integer productId, List<MultipartFile> imageFiles) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Product product = productRepository.findByStoreIdAndId(storeId, productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 각각의 이미지 파일에 대해 처리
        for (MultipartFile imageFile : imageFiles) {
            validateImageFile(imageFile);  // 이미지 검증
            String imageUrl = uploadImageToS3(imageFile);  // 이미지 S3에 업로드

            // ProductPhoto 엔티티 생성 및 저장
            ProductPhoto productPhoto = ProductPhoto.builder()
                    .product(product)  // 조회한 product 객체 사용
                    .src(imageUrl)  // S3 이미지 경로 설정
                    .createdAt(LocalDateTime.now())  // 현재 시간 설정
                    .build();

            productPhotoRepository.save(productPhoto);
        }
    }

    /**
     * 해당 상품의 ProductPhoto 전체 조회
     */
    public List<ReadProductPhotoDTO> getProductPhotosByProductId(Integer productId) {
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductId(productId);
        return productPhotos.stream()
                .map(ReadProductPhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 해당 가게의 모든 ProductPhoto 조회
     */
    @Transactional(readOnly = true)
    public List<ReadProductPhotoDTO> getProductPhotosByStoreId(Integer storeId) {
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductStoreId(storeId);
        return productPhotos.stream()
                .map(ReadProductPhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 전체 ProductPhoto 조회
     */
    @Transactional(readOnly = true)
    public List<ReadProductPhotoDTO> getAllProductPhotos() {
        List<ProductPhoto> productPhotos = productPhotoRepository.findAll();
        return productPhotos.stream()
                .map(ReadProductPhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * ProductPhoto 수정
     */
    public void updateProductPhoto(String token, Integer productId, Integer productPhotoId, List<MultipartFile> imageFiles) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Product product = productRepository.findByStoreIdAndId(storeId, productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // productPhotoId로 ProductPhoto 조회
        ProductPhoto productPhoto = productPhotoRepository.findById(productPhotoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_PHOTO_NOT_FOUND));

        // 이미지가 있을 경우 S3에 업로드 후 URL 변경
        for (MultipartFile image : imageFiles) {
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImageToS3(image);
                productPhoto.changeSrc(imageUrl);  // 이미지 경로 변경
            }
        }

        // 변경된 내용 저장
        productPhotoRepository.save(productPhoto);
    }


    @Transactional
    public void updateProductPhotos(String token, Integer productId, List<MultipartFile> images) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Product product = productRepository.findByStoreIdAndId(storeId, productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 1. 기존 상품 사진 삭제
        List<ProductPhoto> existingPhotos = productPhotoRepository.findByProductId(productId);
        for (ProductPhoto photo : existingPhotos) {
            // S3에서 기존 이미지 삭제 (필요한 경우)
            if (photo.getSrc() != null) {
                s3Service.deleteFile(photo.getSrc());
            }
            // 데이터베이스에서 사진 삭제
            productPhotoRepository.delete(photo);
        }

        // 2. 새로운 사진 업로드 및 저장
        for (MultipartFile imageFile : images) {
            // 이미지 파일 검증
            validateImageFile(imageFile);

            // 이미지 S3에 업로드하고 URL 반환
            String imageUrl = uploadImageToS3(imageFile);

            // 새로운 ProductPhoto 엔티티 생성
            ProductPhoto newPhoto = ProductPhoto.builder()
                    .product(product)  // 조회한 product 객체 설정
                    .src(imageUrl)  // S3에 저장된 이미지 URL 설정
                    .createdAt(LocalDateTime.now())  // 현재 시간 설정
                    .build();

            // 새로운 사진 저장
            productPhotoRepository.save(newPhoto);
        }
    }

    /**
     * ProductPhoto 삭제 (token으로 productId 조회 후 삭제)
     */
    @Transactional
    public void deleteProductPhoto(String token, Integer productId, Integer productPhotoId) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Product product = productRepository.findByStoreIdAndId(storeId, productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // productPhotoId로 ProductPhoto 조회
        ProductPhoto productPhoto = productPhotoRepository.findById(productPhotoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_PHOTO_NOT_FOUND));

        // ProductPhoto 삭제
        productPhotoRepository.delete(productPhoto);
    }

    /**
     * token으로 userId 및 storeId 조회
     */
    private Integer getStoreIdByToken(String token) {
        // userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // storeId 조회
        Integer storeId = storeClient.getStoreIdByUserId(userId);
        if (storeId == null) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        return storeId;
    }

    /**
     * S3에 이미지 업로드
     */
    private String uploadImageToS3(MultipartFile imageFile) {
        try {
            validateImageFile(imageFile);
            String originalFilename = imageFile.getOriginalFilename();
            assert originalFilename != null;
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
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
        }
    }

    /**
     * 이미지 파일 검증
     */
    private void validateImageFile(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.getOriginalFilename() == null) {
            throw new BusinessException(ErrorCode.PRODUCT_PHOTO_NULL);
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
