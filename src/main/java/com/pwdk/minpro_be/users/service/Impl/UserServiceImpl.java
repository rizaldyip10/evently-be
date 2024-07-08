package com.pwdk.minpro_be.users.service.Impl;

import com.pwdk.minpro_be.userRole.service.UserRoleService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.auth.dto.RegisterUserRequestDto;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final VoucherService voucherService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService, VoucherService voucherService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.voucherService = voucherService;
    }

    @Override
    public User register(RegisterUserRequestDto user) {
//        User newUser = user.toEntity();
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist.isPresent()) {
            throw new ApplicationException("User already exist");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);

        var userRegistered  =  userRepository.save(newUser);

        if (!user.getReferralCode().isEmpty() || !user.getReferralCode().isBlank()) {
            Optional<User> referredUser = userRepository.findByReferralCode(user.getReferralCode());
            // TO DO: adding point to user who shared their referral code

            Voucher newUserVoucher = voucherService.getVoucherById(1L);
            voucherService.addUserVoucher(userRegistered, newUserVoucher);
        }

        userRoleService.role(user.getRole(), userRegistered);
        return userRegistered;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException("User not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ApplicationException("User not found"));
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
        if (user.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "User not found");
        }

        var userName = user.get().getName();
        String referralCode = userName.replace(" ", "") + "Evently";
        user.get().setRefferalCode(referralCode);
        userRepository.save(user.get());

        return "Successfully create your referral code";
    }
}
