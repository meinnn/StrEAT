package io.ssafy.p.j11a307.product.global;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucketName}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    // 이미지 파일을 S3에 업로드하고 URL을 반환하는 메서드
    public String uploadFile(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    // S3에서 파일 삭제하는 메서드
    public void deleteFile(String fileUrl) {
        String fileName = extractFileNameFromUrl(fileUrl);
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    // 파일 이름 생성 메서드 (중복 방지)
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }

    // 파일 URL에서 파일 이름을 추출하는 메서드
    private String extractFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}