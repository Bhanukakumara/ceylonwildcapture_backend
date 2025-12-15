package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Service interface for file storage operations.
 * Defines business logic for uploading, downloading, deleting files,
 * and generating presigned URLs for cloud storage (e.g., AWS S3).
 */
public interface FileStorageService {

    /**
     * Upload file to storage.
     *
     * @param file the file to upload
     * @param folder the destination folder/path
     * @return the uploaded file URL
     * @throws RuntimeException if upload fails
     */
    String uploadFile(MultipartFile file, String folder);

    /**
     * Upload file with custom filename.
     *
     * @param file the file to upload
     * @param folder the destination folder/path
     * @param filename the custom filename
     * @return the uploaded file URL
     * @throws RuntimeException if upload fails
     */
    String uploadFile(MultipartFile file, String folder, String filename);

    /**
     * Upload file from File object.
     *
     * @param file the file to upload
     * @param folder the destination folder/path
     * @param filename the filename
     * @return the uploaded file URL
     * @throws RuntimeException if upload fails
     */
    String uploadFile(File file, String folder, String filename);

    /**
     * Upload file from InputStream.
     *
     * @param inputStream the input stream
     * @param folder the destination folder/path
     * @param filename the filename
     * @param contentType the file content type
     * @param contentLength the content length in bytes
     * @return the uploaded file URL
     * @throws RuntimeException if upload fails
     */
    String uploadFile(InputStream inputStream, String folder, String filename,
                     String contentType, long contentLength);

    /**
     * Upload file with metadata.
     *
     * @param file the file to upload
     * @param folder the destination folder/path
     * @param filename the filename
     * @param metadata the file metadata (key-value pairs)
     * @return the uploaded file URL
     * @throws RuntimeException if upload fails
     */
    String uploadFileWithMetadata(MultipartFile file, String folder, String filename,
                                  Map<String, String> metadata);

    /**
     * Delete file from storage.
     *
     * @param fileUrl the file URL to delete
     * @throws RuntimeException if deletion fails
     */
    void deleteFile(String fileUrl);

    /**
     * Delete file by key/path.
     *
     * @param folder the folder/path
     * @param filename the filename
     * @throws RuntimeException if deletion fails
     */
    void deleteFile(String folder, String filename);

    /**
     * Delete multiple files.
     *
     * @param fileUrls the list of file URLs to delete
     * @throws RuntimeException if deletion fails
     */
    void deleteFiles(String[] fileUrls);

    /**
     * Check if file exists in storage.
     *
     * @param fileUrl the file URL
     * @return true if file exists
     */
    boolean fileExists(String fileUrl);

    /**
     * Check if file exists by key/path.
     *
     * @param folder the folder/path
     * @param filename the filename
     * @return true if file exists
     */
    boolean fileExists(String folder, String filename);

    /**
     * Generate presigned URL for file upload.
     *
     * @param folder the destination folder/path
     * @param filename the filename
     * @param expirationMinutes the URL expiration time in minutes
     * @return the presigned upload URL
     * @throws RuntimeException if URL generation fails
     */
    String generatePresignedUploadUrl(String folder, String filename, int expirationMinutes);

    /**
     * Generate presigned URL for file download.
     *
     * @param fileUrl the file URL
     * @param expirationMinutes the URL expiration time in minutes
     * @return the presigned download URL
     * @throws RuntimeException if URL generation fails
     */
    String generatePresignedDownloadUrl(String fileUrl, int expirationMinutes);

    /**
     * Generate presigned URL for file download by key.
     *
     * @param folder the folder/path
     * @param filename the filename
     * @param expirationMinutes the URL expiration time in minutes
     * @return the presigned download URL
     * @throws RuntimeException if URL generation fails
     */
    String generatePresignedDownloadUrl(String folder, String filename, int expirationMinutes);

    /**
     * Get file URL (public or presigned).
     *
     * @param folder the folder/path
     * @param filename the filename
     * @return the file URL
     */
    String getFileUrl(String folder, String filename);

    /**
     * Get public file URL.
     *
     * @param folder the folder/path
     * @param filename the filename
     * @return the public file URL
     */
    String getPublicFileUrl(String folder, String filename);

    /**
     * Download file from storage.
     *
     * @param fileUrl the file URL
     * @return the downloaded file as byte array
     * @throws RuntimeException if download fails
     */
    byte[] downloadFile(String fileUrl);

    /**
     * Download file to local filesystem.
     *
     * @param fileUrl the file URL
     * @param destinationPath the local destination path
     * @return the downloaded file
     * @throws RuntimeException if download fails
     */
    File downloadFileToLocal(String fileUrl, String destinationPath);

    /**
     * Copy file within storage.
     *
     * @param sourceUrl the source file URL
     * @param destinationFolder the destination folder
     * @param destinationFilename the destination filename
     * @return the copied file URL
     * @throws RuntimeException if copy fails
     */
    String copyFile(String sourceUrl, String destinationFolder, String destinationFilename);

    /**
     * Move file within storage.
     *
     * @param sourceUrl the source file URL
     * @param destinationFolder the destination folder
     * @param destinationFilename the destination filename
     * @return the moved file URL
     * @throws RuntimeException if move fails
     */
    String moveFile(String sourceUrl, String destinationFolder, String destinationFilename);

    /**
     * Get file size in bytes.
     *
     * @param fileUrl the file URL
     * @return the file size in bytes
     * @throws RuntimeException if size retrieval fails
     */
    long getFileSize(String fileUrl);

    /**
     * Get file metadata.
     *
     * @param fileUrl the file URL
     * @return map of file metadata
     * @throws RuntimeException if metadata retrieval fails
     */
    Map<String, String> getFileMetadata(String fileUrl);

    /**
     * Update file metadata.
     *
     * @param fileUrl the file URL
     * @param metadata the new metadata
     * @throws RuntimeException if metadata update fails
     */
    void updateFileMetadata(String fileUrl, Map<String, String> metadata);

    /**
     * Set file access permissions.
     *
     * @param fileUrl the file URL
     * @param isPublic whether file should be publicly accessible
     * @throws RuntimeException if permission update fails
     */
    void setFilePermissions(String fileUrl, boolean isPublic);

    /**
     * Generate unique filename.
     *
     * @param originalFilename the original filename
     * @return the unique filename with timestamp or UUID
     */
    String generateUniqueFilename(String originalFilename);

    /**
     * Extract file extension from filename.
     *
     * @param filename the filename
     * @return the file extension
     */
    String getFileExtension(String filename);

    /**
     * Validate file size.
     *
     * @param file the file to validate
     * @param maxSizeInBytes the maximum allowed size in bytes
     * @return true if file size is within limit
     */
    boolean isFileSizeValid(MultipartFile file, long maxSizeInBytes);

    /**
     * Validate file type.
     *
     * @param file the file to validate
     * @param allowedTypes the list of allowed MIME types
     * @return true if file type is allowed
     */
    boolean isFileTypeValid(MultipartFile file, String[] allowedTypes);

    /**
     * Get storage bucket/container name.
     *
     * @return the storage bucket name
     */
    String getStorageBucket();

    /**
     * Get storage base URL.
     *
     * @return the storage base URL
     */
    String getStorageBaseUrl();
}
