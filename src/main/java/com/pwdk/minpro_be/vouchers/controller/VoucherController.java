package com.pwdk.minpro_be.vouchers.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.vouchers.dto.CreateVoucherRequestDto;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.data.domain.PageRequest;
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
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        var createdEventVoucher = voucherService.createEventVoucher(createVoucherRequestDto, eventSlug, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventVoucher);
    }

    @GetMapping("/{eventSlug}")
    private ResponseEntity<?> getEventVouchers(@PathVariable("eventSlug") String eventSlug) {
        return ResponseEntity.status(HttpStatus.OK).body(voucherService.getEventVouchers(eventSlug));
    }

    @GetMapping("/user-voucher")
    public ResponseEntity<?> getUserVouchers() {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return ResponseEntity.status(HttpStatus.OK).body(voucherService.getUserVouchers(email));
    }

    @GetMapping("/transaction/{eventSlug}")
    public ResponseEntity<?> getActiveTrxVoucher(
            @PathVariable("eventSlug") String eventSlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        var pageable = PageRequest.of(page, size);

        var trxVoucher = voucherService.getActiveTrxVoucher(eventSlug, email, pageable);
        return Response.success("Fetched trx voucher list", trxVoucher);
    }
}
