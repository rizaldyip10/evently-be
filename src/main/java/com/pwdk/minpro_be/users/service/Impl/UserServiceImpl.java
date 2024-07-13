package com.pwdk.minpro_be.users.service.Impl;

import com.pwdk.minpro_be.exception.DataConflictException;
import com.pwdk.minpro_be.exception.DataNotFoundException;
import com.pwdk.minpro_be.point.service.PointService;
import com.pwdk.minpro_be.userRole.service.UserRoleService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.auth.dto.RegisterUserRequestDto;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final VoucherService voucherService;
    private final PointService pointService;


    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Lazy UserRoleService userRoleService,
            @Lazy VoucherService voucherService,
            @Lazy PointService pointService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.voucherService = voucherService;
        this.pointService = pointService;
    }

    @Override
    @Transactional
    public User register(RegisterUserRequestDto user) {
//        User newUser = user.toEntity();
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist.isPresent()) {
            throw new DataConflictException("User already exist");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);

        var userRegistered  =  userRepository.save(newUser);

        if (user.getReferralCode() != null) {
            Optional<User> referredUser = userRepository.findByReferralCode(user.getReferralCode());
            if (referredUser.isEmpty()) {
                throw new DataNotFoundException("User not found");
            }

            pointService.addUserPoint(referredUser.get().getId(), 10000.0);

            Voucher newUserVoucher = voucherService.getVoucherById(1L);
            voucherService.addUserVoucher(userRegistered, newUserVoucher);
        }

        userRoleService.role(user.getRole(), userRegistered);
        return userRegistered;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("User not found"));
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deletedAt(Long id) {
    }

    @Override
    public User profile() {
        return null;
    }

    @Override
    public String generateReferralCode(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Date today = new Date();
        if (user.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }

        var userName = user.get().getName();
        String referralCode = userName.replace(" ", "") + "Evently" + today.toString();
        user.get().setReferralCode(referralCode);
        userRepository.save(user.get());

        return "Successfully create your referral code";
    }
}
