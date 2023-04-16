package ittalents.dominos;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;

import java.io.InputStream;

@SpringBootApplication
public class DominosApplication {
    public static void main(String[] args) {
        SpringApplication.run(DominosApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
//
//    @Bean
//    public JavaMailSender javaMailSender(){
//        return new JavaMailSender() {
//            @Override
//            public void send(SimpleMailMessage simpleMessage) throws MailException {
//
//            }
//
//            @Override
//            public void send(SimpleMailMessage... simpleMessages) throws MailException {
//
//            }
//
//            @Override
//            public MimeMessage createMimeMessage() {
//                return null;
//            }
//
//            @Override
//            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
//                return null;
//            }
//
//            @Override
//            public void send(MimeMessage mimeMessage) throws MailException {
//
//            }
//
//            @Override
//            public void send(MimeMessage... mimeMessages) throws MailException {
//
//            }
//
//            @Override
//            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
//
//            }
//
//            @Override
//            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
//
//            }
//        };
//    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
