package org.bashtan.MyApps.data.services.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.data.entities.EmailVerificationTokenEntity;
import org.bashtan.MyApps.data.entities.UserEntity;
import org.bashtan.MyApps.data.repositories.EmailVerificationTokenRepository;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.bashtan.MyApps.data.services.interfaces.EmailServiceInterface;
import org.bashtan.MyApps.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements EmailServiceInterface {

    @Value("${server.ip}")
    private String SERVER_IP;

    private final static String API = "/api/v1/email/confirmEmail";
    private final JavaMailSender mailSender;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final Environment environment;

    @Override
    public void sendEmail(UserDTO userDTO) {
        String token = getToken(userDTO);
        sendEmailConfirmation(userDTO.getEmail(), token);
        log.info("Send Email");
    }

    @Override
    public String confirmEmail(String token) {
        return emailVerificationTokenRepository.findByToken(token)
                .map((emailVerificationToken -> {
                            if (emailVerificationToken.isExpired()) {
                                return "Token expired!";
                            }
                            UserEntity user = emailVerificationToken.getUser();
                            user.setEmailVerified(true);
                            userRepository.save(user);
                            emailVerificationTokenRepository.delete(emailVerificationToken);
                            log.info("Email successfully confirmed!");
                            return "Email successfully confirmed!";
                        })
                ).orElse("Token not found!");
    }

    @Override
    public void resendEmail(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .map(UserMapper::toUserDTO)
                .ifPresent((findUser) ->
                {
                    deleteToken(findUser);
                    sendEmail(findUser);
                });
    }

    @Override
    public void deleteToken(UserDTO userDTO) {
        emailVerificationTokenRepository.findByUserId(userDTO.getId())
                .ifPresent(emailVerificationTokenRepository::delete);
        log.info("Delete token!");
    }

    @Override
    @SneakyThrows
    public void sendResponseAndroid(String email) {
        String subject = "Download APP Church River of Life";
        String url = "https://play.google.com/apps/testing/com.bashtandrey.churchapp";
        String text = String.format("Url download %s:", url);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(mimeMessage);
    }

    private String getToken(UserDTO userDTO) {
        EmailVerificationTokenEntity emailToken =
                emailVerificationTokenRepository.findByUserId(userDTO.getId()).orElseGet(() ->
                        emailVerificationTokenRepository.save(
                                EmailVerificationTokenEntity.builder()
                                        .token(EmailVerificationTokenEntity.generateToken())
                                        .user(UserMapper.toUserEntity(userDTO))
                                        .expiryDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                                        .build())
                );
        log.info("Get Token");
        return emailToken.getToken();
    }

    @SneakyThrows
    private void sendEmailConfirmation(String email, String token) {
        String confirmationUrl = String.format("%s/api/v1/email/confirmEmail?token=%s", SERVER_IP, token);
        String subject = "Email Confirmation APP Church River of Life";
        String textEn = "Please confirm your email by clicking on the following link: " + confirmationUrl;
        String textRU = "Пожалуйста, подтвердите свой адрес электронной почты, нажав на следующую ссылку: " + confirmationUrl;
        String textUA = "Будь ласка, підтвердіть свою електронну адресу, натиснувши на наступне посилання: " + confirmationUrl;

        String text = String.format("<p>%s</p><p>%s</p><p>%s</p>", textEn, textUA, textRU
        );
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(mimeMessage);
    }
}




