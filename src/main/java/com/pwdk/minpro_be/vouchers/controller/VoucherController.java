package com.pwdk.minpro_be.vouchers.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.vouchers.dto.CreateVoucherRequestDto;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/{event-slug}")
    public ResponseEntity<?> createEventVoucher(
            @RequestBody CreateVoucherRequestDto createVoucherRequestDto,
            @PathVariable("event-slug") String eventSlug
    ) {
        var createdEventVoucher = voucherService.createEventVoucher(createVoucherRequestDto, eventSlug);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventVoucher);
    }

    public ResponseEntity<?> getUserVouchers() {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return ResponseEntity.status(HttpStatus.OK).body(voucherService.getUserVouchers(email));
    }
}
