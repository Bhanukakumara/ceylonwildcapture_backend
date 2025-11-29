package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.UserManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.UserManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Override
    public User manageUser(UserManagementDto managementDto, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User banUser(Long userId, String reason, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User unbanUser(Long userId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User activateUser(Long userId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User deactivateUser(Long userId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User assignRole(Long userId, UserRole newRole, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<User> getVerifiedPhotographers(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<User> getUnverifiedPhotographers(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<User> getBannedUsers(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<User> getInactiveUsers(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public User verifyPhotographer(Long userId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User unverifyPhotographer(Long userId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<User> getUserDetails(Long userId) {
        // TODO: Implement actual business logic
        return Optional.empty();
    }

    @Override
    public Page<User> getAllUsers(UserRole role, Boolean isActive, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }
}
