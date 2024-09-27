package com.tutorial.identity.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.tutorial.identity.dto.request.AuthenticationRequest;
import com.tutorial.identity.dto.request.IntrospectRequest;
import com.tutorial.identity.dto.response.AuthenticationResponse;
import com.tutorial.identity.dto.response.IntrospectResponse;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.Errorcode;
import com.tutorial.identity.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final StaffRepository staffRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "+Fua0PEGbJttzuhBFkrMyGyNMIGmqQhxaPAGDjRRiJiOuikiiNOBC6V+GtW/DifN";

    public IntrospectResponse introspect(IntrospectRequest introspectRequest){
        var token = introspectRequest.getToken();

        try{
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        } catch (JOSEException exception){
            throw new RuntimeException(exception);
        }
        return null;
    }

    public AuthenticationResponse isAuthenticate(AuthenticationRequest request){
        var staff = staffRepository.findStaffByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(Errorcode.USER_NOTFOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassWord(), staff.getPassWord());

        if(!authenticated){
            throw new AppException(Errorcode.UNAUTHENTICATED);
        }

        var token = generateToken(request.getUserName());

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    private String generateToken(String username){
        // Noi dung thuat toan
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Body: issuer-ai la nguoi dinh danh, expirationTime-thoi gian het han
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)  //username
                .issuer("quoc hao")  //
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException exception){
            log.error("Cannot create token");
            throw new RuntimeException(exception);
        }

    }
}
