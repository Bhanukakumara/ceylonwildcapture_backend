package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for photo response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDto {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String thumbnailUrl;
    private String watermarkedUrl;
    private Long photographerId;
    private String photographerName;
    private Long fileSize;
    private Integer width;
    private Integer height;
    private String format;
    private BigDecimal basePrice;
    private BigDecimal commercialPrice;
    private BigDecimal editorialPrice;
    private BigDecimal extendedPrice;
    private Boolean isApproved;
    private Boolean isFeatured;
    private Boolean isActive;
    private Integer viewCount;
    private Integer downloadCount;
    private Integer likeCount;
    private String location;
    private String cameraModel;
    private String lens;
    private String focalLength;
    private String aperture;
    private String shutterSpeed;
    private String iso;
    private LocalDateTime captureDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TagResponseDto> tags;
    private List<CategoryResponseDto> categories;

    /**
     * Static method to convert Photo entity to PhotoResponseDto.
     *
     * @param photo the photo entity
     * @return PhotoResponseDto
     */
    public static PhotoResponseDto fromEntity(Photo photo) {
        return fromEntitySecure(photo, false);
    }

    /**
     * Static method to convert Photo entity to PhotoResponseDto with security
     * controls.
     * Only includes full-resolution imageUrl if user has purchased the photo.
     *
     * @param photo               the photo entity
     * @param includePurchasedUrl whether to include the full-resolution imageUrl
     *                            (only for purchasers)
     * @return PhotoResponseDto
     */
    public static PhotoResponseDto fromEntitySecure(Photo photo, boolean includePurchasedUrl) {
        if (photo == null) {
            return null;
        }

        return PhotoResponseDto.builder()
                .id(photo.getId())
                .title(photo.getTitle())
                .description(photo.getDescription())
                // Only include full imageUrl if user has purchased
                .imageUrl(includePurchasedUrl ? photo.getImageUrl() : null)
                // Always include watermarked and thumbnail URLs for preview
                .thumbnailUrl(photo.getThumbnailUrl())
                .watermarkedUrl(photo.getWatermarkedUrl())
                .photographerId(photo.getPhotographer() != null ? photo.getPhotographer().getId() : null)
                .photographerName(photo.getPhotographer() != null ? photo.getPhotographer().getFirstName() : null)
                .fileSize(photo.getFileSize())
                .width(photo.getWidth())
                .height(photo.getHeight())
                .format(photo.getFormat())
                .basePrice(photo.getBasePrice())
                .commercialPrice(photo.getCommercialPrice())
                .editorialPrice(photo.getEditorialPrice())
                .extendedPrice(photo.getExtendedPrice())
                .isApproved(photo.getIsApproved())
                .isFeatured(photo.getIsFeatured())
                .isActive(photo.getIsActive())
                .viewCount(photo.getViewCount())
                .downloadCount(photo.getDownloadCount())
                .likeCount(photo.getLikeCount())
                .location(photo.getLocation())
                .cameraModel(photo.getCameraModel())
                .lens(photo.getLens())
                .focalLength(photo.getFocalLength())
                .aperture(photo.getAperture())
                .shutterSpeed(photo.getShutterSpeed())
                .iso(photo.getIso())
                .captureDate(photo.getCaptureDate())
                .createdAt(photo.getCreatedAt())
                .updatedAt(photo.getUpdatedAt())
                .tags(photo.getTags() != null ? photo.getTags().stream()
                        .map(TagResponseDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .categories(photo.getCategories() != null ? photo.getCategories().stream()
                        .map(CategoryResponseDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    /**
     * Static method to convert Photo entity to PhotoResponseDto without nested
     * collections.
     *
     * @param photo the photo entity
     * @return PhotoResponseDto without tags and categories
     */
    public static PhotoResponseDto fromEntitySimple(Photo photo) {
        if (photo == null) {
            return null;
        }

        return PhotoResponseDto.builder()
                .id(photo.getId())
                .title(photo.getTitle())
                .description(photo.getDescription())
                .imageUrl(photo.getImageUrl())
                .thumbnailUrl(photo.getThumbnailUrl())
                .watermarkedUrl(photo.getWatermarkedUrl())
                .photographerId(photo.getPhotographer() != null ? photo.getPhotographer().getId() : null)
                .photographerName(photo.getPhotographer() != null ? photo.getPhotographer().getFirstName() : null)
                .fileSize(photo.getFileSize())
                .width(photo.getWidth())
                .height(photo.getHeight())
                .format(photo.getFormat())
                .basePrice(photo.getBasePrice())
                .commercialPrice(photo.getCommercialPrice())
                .editorialPrice(photo.getEditorialPrice())
                .extendedPrice(photo.getExtendedPrice())
                .isApproved(photo.getIsApproved())
                .isFeatured(photo.getIsFeatured())
                .isActive(photo.getIsActive())
                .viewCount(photo.getViewCount())
                .downloadCount(photo.getDownloadCount())
                .likeCount(photo.getLikeCount())
                .location(photo.getLocation())
                .cameraModel(photo.getCameraModel())
                .lens(photo.getLens())
                .focalLength(photo.getFocalLength())
                .aperture(photo.getAperture())
                .shutterSpeed(photo.getShutterSpeed())
                .iso(photo.getIso())
                .captureDate(photo.getCaptureDate())
                .createdAt(photo.getCreatedAt())
                .updatedAt(photo.getUpdatedAt())
                .build();
    }

    /**
     * Convert a list of Photo entities to a list of PhotoResponseDtos.
     *
     * @param photos the list of photo entities
     * @return list of PhotoResponseDtos
     */
    public static List<PhotoResponseDto> fromEntityList(List<Photo> photos) {
        if (photos == null) {
            return null;
        }
        return photos.stream()
                .map(PhotoResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Convert a Page of Photo entities to a Page of PhotoResponseDtos.
     *
     * @param photoPage the page of photo entities
     * @return page of PhotoResponseDtos
     */
    public static Page<PhotoResponseDto> fromEntityPage(Page<Photo> photoPage) {
        if (photoPage == null) {
            return null;
        }
        return photoPage.map(PhotoResponseDto::fromEntity);
    }
}
