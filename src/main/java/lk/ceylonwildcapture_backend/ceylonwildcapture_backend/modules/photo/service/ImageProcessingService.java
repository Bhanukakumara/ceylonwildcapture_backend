package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Service interface for image processing operations.
 * Defines business logic for generating thumbnails, applying watermarks,
 * extracting EXIF data, and image transformations.
 */
public interface ImageProcessingService {

    /**
     * Generate thumbnail from image file.
     *
     * @param imageFile the original image file
     * @param width the thumbnail width
     * @param height the thumbnail height
     * @return the thumbnail file
     * @throws RuntimeException if thumbnail generation fails
     */
    File generateThumbnail(File imageFile, int width, int height);

    /**
     * Generate thumbnail from image file with aspect ratio preservation.
     *
     * @param imageFile the original image file
     * @param maxDimension the maximum dimension (width or height)
     * @return the thumbnail file
     * @throws RuntimeException if thumbnail generation fails
     */
    File generateThumbnail(File imageFile, int maxDimension);

    /**
     * Generate thumbnail from MultipartFile.
     *
     * @param multipartFile the uploaded file
     * @param width the thumbnail width
     * @param height the thumbnail height
     * @return the thumbnail file
     * @throws RuntimeException if thumbnail generation fails
     */
    File generateThumbnail(MultipartFile multipartFile, int width, int height);

    /**
     * Generate thumbnail from InputStream.
     *
     * @param inputStream the image input stream
     * @param width the thumbnail width
     * @param height the thumbnail height
     * @param format the image format (jpg, png, etc.)
     * @return the thumbnail file
     * @throws RuntimeException if thumbnail generation fails
     */
    File generateThumbnail(InputStream inputStream, int width, int height, String format);

    /**
     * Apply watermark to image.
     *
     * @param imageFile the original image file
     * @param watermarkText the watermark text
     * @return the watermarked image file
     * @throws RuntimeException if watermark application fails
     */
    File applyWatermark(File imageFile, String watermarkText);

    /**
     * Apply watermark image to image.
     *
     * @param imageFile the original image file
     * @param watermarkImageFile the watermark image file
     * @param opacity the watermark opacity (0.0 to 1.0)
     * @return the watermarked image file
     * @throws RuntimeException if watermark application fails
     */
    File applyWatermarkImage(File imageFile, File watermarkImageFile, float opacity);

    /**
     * Apply watermark with custom position.
     *
     * @param imageFile the original image file
     * @param watermarkText the watermark text
     * @param position the watermark position (TOP_LEFT, CENTER, BOTTOM_RIGHT, etc.)
     * @param opacity the watermark opacity (0.0 to 1.0)
     * @return the watermarked image file
     * @throws RuntimeException if watermark application fails
     */
    File applyWatermark(File imageFile, String watermarkText, String position, float opacity);

    /**
     * Extract EXIF metadata from image.
     *
     * @param imageFile the image file
     * @return map of EXIF data (key-value pairs)
     * @throws RuntimeException if EXIF extraction fails
     */
    Map<String, String> extractExifData(File imageFile);

    /**
     * Extract EXIF metadata from MultipartFile.
     *
     * @param multipartFile the uploaded image file
     * @return map of EXIF data (key-value pairs)
     * @throws RuntimeException if EXIF extraction fails
     */
    Map<String, String> extractExifData(MultipartFile multipartFile);

    /**
     * Extract EXIF metadata from InputStream.
     *
     * @param inputStream the image input stream
     * @return map of EXIF data (key-value pairs)
     * @throws RuntimeException if EXIF extraction fails
     */
    Map<String, String> extractExifData(InputStream inputStream);

    /**
     * Extract camera model from image.
     *
     * @param imageFile the image file
     * @return the camera model
     */
    String extractCameraModel(File imageFile);

    /**
     * Extract GPS coordinates from image.
     *
     * @param imageFile the image file
     * @return array containing [latitude, longitude], or null if not available
     */
    Double[] extractGpsCoordinates(File imageFile);

    /**
     * Extract capture date from image.
     *
     * @param imageFile the image file
     * @return the capture date as string
     */
    String extractCaptureDate(File imageFile);

    /**
     * Get image dimensions.
     *
     * @param imageFile the image file
     * @return array containing [width, height]
     * @throws RuntimeException if dimension extraction fails
     */
    int[] getImageDimensions(File imageFile);

    /**
     * Get image dimensions from MultipartFile.
     *
     * @param multipartFile the uploaded image file
     * @return array containing [width, height]
     * @throws RuntimeException if dimension extraction fails
     */
    int[] getImageDimensions(MultipartFile multipartFile);

    /**
     * Resize image to specific dimensions.
     *
     * @param imageFile the original image file
     * @param width the target width
     * @param height the target height
     * @param maintainAspectRatio whether to maintain aspect ratio
     * @return the resized image file
     * @throws RuntimeException if resize fails
     */
    File resizeImage(File imageFile, int width, int height, boolean maintainAspectRatio);

    /**
     * Compress image to reduce file size.
     *
     * @param imageFile the original image file
     * @param quality the compression quality (0.0 to 1.0)
     * @return the compressed image file
     * @throws RuntimeException if compression fails
     */
    File compressImage(File imageFile, float quality);

    /**
     * Convert image format.
     *
     * @param imageFile the original image file
     * @param targetFormat the target format (jpg, png, webp, etc.)
     * @return the converted image file
     * @throws RuntimeException if conversion fails
     */
    File convertImageFormat(File imageFile, String targetFormat);

    /**
     * Crop image to specified dimensions.
     *
     * @param imageFile the original image file
     * @param x the x-coordinate of the crop area
     * @param y the y-coordinate of the crop area
     * @param width the width of the crop area
     * @param height the height of the crop area
     * @return the cropped image file
     * @throws RuntimeException if crop fails
     */
    File cropImage(File imageFile, int x, int y, int width, int height);

    /**
     * Remove EXIF data from image.
     *
     * @param imageFile the original image file
     * @return the image file without EXIF data
     * @throws RuntimeException if EXIF removal fails
     */
    File removeExifData(File imageFile);

    /**
     * Validate image file.
     *
     * @param imageFile the image file to validate
     * @return true if image is valid
     */
    boolean isValidImage(File imageFile);

    /**
     * Validate image format.
     *
     * @param multipartFile the uploaded file
     * @param allowedFormats the list of allowed formats
     * @return true if format is allowed
     */
    boolean isValidImageFormat(MultipartFile multipartFile, String[] allowedFormats);

    /**
     * Get image file size in bytes.
     *
     * @param imageFile the image file
     * @return the file size in bytes
     */
    long getImageFileSize(File imageFile);

    /**
     * Get image format.
     *
     * @param imageFile the image file
     * @return the image format (jpg, png, etc.)
     */
    String getImageFormat(File imageFile);

    /**
     * Process image for web (optimize, resize, compress).
     *
     * @param imageFile the original image file
     * @param maxWidth the maximum width
     * @param maxHeight the maximum height
     * @param quality the compression quality
     * @return the processed image file
     * @throws RuntimeException if processing fails
     */
    File processForWeb(File imageFile, int maxWidth, int maxHeight, float quality);
}
