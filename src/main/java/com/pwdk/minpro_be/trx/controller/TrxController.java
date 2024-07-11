package com.pwdk.minpro_be.trx.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.trx.dto.PaymentRequestDto;
import com.pwdk.minpro_be.trx.dto.TicketTrxRequestDto;
import com.pwdk.minpro_be.trx.service.TrxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TrxController {
    private final TrxService trxService;

    public TrxController(TrxService trxService) {
        this.trxService = trxService;
    }

    @PostMapping("/{eventSlug}")
    public ResponseEntity<?> createUserTrx(@PathVariable("eventSlug") String eventSlug) {
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        return Response.success(HttpStatus.CREATED.value(), "Transaction Created", trxService.createUserTrx(userEmail, eventSlug));
    }

    @PutMapping("/{eventSlug}/{trxId}")
    public ResponseEntity<?> addUserTicketSelection(
            @RequestBody TicketTrxRequestDto requestDto,
            @PathVariable("eventSlug") String eventSlug,
            @PathVariable("trxId") Long trxId)
    {
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        return Response.success("Transaction Updated", trxService.addingTrxItem(requestDto, userEmail, eventSlug, trxId));
    }

    @PatchMapping("/{eventSlug}/{trxId}")
    public ResponseEntity<?> addUserPayment(
            @RequestBody PaymentRequestDto requestDto,
            @PathVariable("eventSlug") String eventSlug,
            @PathVariable("trxId") Long trxId)
    {
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        return Response.success("Transaction Complete", trxService.addingPaymentMethod(requestDto, userEmail, eventSlug, trxId));
    }

    @GetMapping("/detail/{trxId}")
    public ResponseEntity<?> getTrxDetail(@PathVariable("trxId") Long trxId) {
        return Response.success("Transaction detail fetched", trxService.getTrxDetail(trxId));
    }

    @GetMapping("/event/{eventSlug}")
    public ResponseEntity<?> getEventTrx(@PathVariable("eventSlug") String eventSlug) {
        return Response.success("Event transaction list fetched", trxService.getEventTrx(eventSlug));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserTrx() {
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        return Response.success("User transaction list fetched", trxService.getUserTrx(userEmail));
    }

    @DeleteMapping("/user/{trxId}")
    public ResponseEntity<?> deleteUserTrx(@PathVariable("trxId") Long trxId) {
        var claims = Claims.getClaimsFromJwt();
        var userEmail = (String) claims.get("sub");

        return Response.success("User transaction deleted", trxService.deleteUserTrx(userEmail, trxId));
    }
}
