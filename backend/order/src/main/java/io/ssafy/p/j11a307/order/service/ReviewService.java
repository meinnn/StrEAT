package io.ssafy.p.j11a307.order.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import io.ssafy.p.j11a307.order.dto.CreateReviewDTO;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.entity.OrdersId;
import io.ssafy.p.j11a307.order.entity.Review;
import io.ssafy.p.j11a307.order.entity.ReviewPhoto;
import io.ssafy.p.j11a307.order.exception.BusinessException;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import io.ssafy.p.j11a307.order.repository.ReviewPhotoRepository;
import io.ssafy.p.j11a307.order.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final OwnerClient ownerClient;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public void createReview(Integer id, CreateReviewDTO createReviewDTO, String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        Integer score = createReviewDTO.score();
        String content = createReviewDTO.content();

        //1. 주문 id가 유효하지 않다면?
        Orders orders =ordersRepository.findById(id).orElse(null);
        if(orders == null) throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);


        //2. 해당 주문을 한 유저가 아니라면?
        if(orders.getUserId() != userId) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        Review review = Review.builder()
                .id(new OrdersId(orders))
                .score(score)
                .content(content).build();

        reviewRepository.save(review);
        
        //리뷰 이미지 저장
        for (MultipartFile image : createReviewDTO.images()) {
            if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
                throw new BusinessException(ErrorCode.FileEmptyException);
            }

            String url = this.uploadImage(image);
            
            ReviewPhoto reviewPhoto = ReviewPhoto.builder()
                    .reviewId(review)
                    .src(url)
                    .build();

            reviewPhotoRepository.save(reviewPhoto);
        }
    }

    @Transactional
    public void deleteReview(Integer id, String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        Orders orders = ordersRepository.findById(id).orElse(null);

        //1. 해당 리뷰가 존재하지 않는다면?
        Review review = reviewRepository.searchReview(id);
        if(review == null) throw new BusinessException(ErrorCode.REVIEW_NOT_FOUND);

        //2. 삭제할 권한이 없다면?
        if(orders.getUserId() != userId) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        //사진은 cascade를 통해 삭제됨
        reviewRepository.delete(review);
    }










    private String uploadImage(MultipartFile image) {
        //image 파일 확장자 검사
        this.validateImageFileExtension(image.getOriginalFilename());
        return this.uploadToS3(image);
    }

    private void validateImageFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");

        //확장자가 존재하지 않음
        if (lastDotIndex == -1) throw new BusinessException(ErrorCode.S3Exception);

        String extention = fileName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        //확장자가 유효하지 않음
        if (!allowedExtentionList.contains(extention)) throw new BusinessException(ErrorCode.S3Exception);
    }

    private String uploadToS3(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String s3Filename = UUID.randomUUID().toString().substring(0,10) + originalFilename;
        String url = "";

        try {
            InputStream is = image.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);
            ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
            metadata.setContentType("image/" + extension);
            metadata.setContentLength(bytes.length);

            //S3에 요청할 때 사용할 byteInputStream 생성
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3Filename, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            //실제로 S3에 이미지 데이터를 넣는 부분
            amazonS3.putObject(putObjectRequest);
            url = amazonS3.getUrl(bucketName, s3Filename).toString();

            return url;
        } catch(Exception e) {
            throw new BusinessException(ErrorCode.S3Exception);
        }
    }
}
