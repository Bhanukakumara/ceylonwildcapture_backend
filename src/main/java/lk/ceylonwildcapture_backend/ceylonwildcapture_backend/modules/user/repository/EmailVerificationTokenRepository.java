package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for EmailVerificationToken entity.
 */
@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    /**
     * Find token by token string.
     */
    Optional<EmailVerificationToken> findByToken(String token);

    /**
     * Find token by user.
     */
    Optional<EmailVerificationToken> findByUser(User user);

    /**
     * Delete all expired tokens.
     */
    @Modifying
    @Query("DELETE FROM EmailVerificationToken t WHERE t.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);

    /**
     * Delete all tokens for a user.
     */
    void deleteByUser(User user);
}
