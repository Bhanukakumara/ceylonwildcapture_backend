package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Filter class for date range queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {

    /**
     * Start date (inclusive)
     */
    private LocalDateTime startDate;

    /**
     * End date (inclusive)
     */
    private LocalDateTime endDate;

    /**
     * Check if date range is valid.
     *
     * @return true if both dates are set and start is before end
     */
    public boolean isValid() {
        return startDate != null && endDate != null && startDate.isBefore(endDate);
    }
}
