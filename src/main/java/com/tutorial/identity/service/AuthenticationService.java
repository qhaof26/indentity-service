package com.tutorial.identity.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tutorial.identity.dto.request.AuthenticationRequest;
import com.tutorial.identity.dto.request.IntrospectRequest;
import com.tutorial.identity.dto.response.AuthenticationResponse;
import com.tutorial.identity.dto.response.IntrospectResponse;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.Errorcode;
import com.tutorial.identity.repository.StaffRepository;
import com.tutorial.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
            throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .authenticated(verified && expiryTime.after(new Date()))
                //.token(token)
                .build();
    }

    public AuthenticationResponse isAuthenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUserName())
                .orElseThrow(()-> new AppException(Errorcode.USER_NOTFOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassWord(), user.getPassword());

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
                .issuer("qhaofdev")  //
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
