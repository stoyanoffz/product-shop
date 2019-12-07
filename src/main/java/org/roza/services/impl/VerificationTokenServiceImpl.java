package org.roza.services.impl;

import org.modelmapper.ModelMapper;
import org.roza.domain.entities.User;
import org.roza.domain.entities.VerificationToken;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.exceptions.ExpiredTokenException;
import org.roza.repositories.VerificationTokenRepository;
import org.roza.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final ModelMapper mapper;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository,
                                        ModelMapper mapper) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.mapper = mapper;
    }

    @Override
    public VerificationTokenServiceModel createRegistrationToken(UserServiceModel userServiceModel) {
        this.verificationTokenRepository.saveAndFlush(buildRegistrationToken(userServiceModel));

        return findTokenByUserId(userServiceModel.getId());
    }

    @Override
    public void checkToken(String tokenId) throws ExpiredTokenException {
        Optional<VerificationToken> token = Optional.ofNullable(
                this.verificationTokenRepository.findById(tokenId).orElseThrow(() ->
                new ExpiredTokenException("Token is invalid or has expired")));

        boolean hasTokenExpired = new Date().after(token.get().getExpirationDate());

        if (hasTokenExpired) {
            throw new ExpiredTokenException("Token is invalid or has expired");
        }
    }

    private VerificationToken buildRegistrationToken(UserServiceModel userServiceModel) {
        VerificationToken token = new VerificationToken();
        token.setUser(this.mapper.map(userServiceModel, User.class));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 12);

        token.setExpirationDate(calendar.getTime());

        return token;
    }

    private String generateToken() {
        String code = UUID.randomUUID().toString();
        return code.substring(code.lastIndexOf("-") + 1);
    }

    private VerificationTokenServiceModel findTokenByUserId(String userId) {
        return this.mapper.map(
                this.verificationTokenRepository.findOneByUserId(userId),
                VerificationTokenServiceModel.class);
    }
}
