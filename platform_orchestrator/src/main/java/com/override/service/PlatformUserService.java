package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.models.Authority;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.repositories.PlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PlatformUserService {

    @Autowired
    private PlatformUserRepository accountRepository;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;

    public PlatformUser getAccountByChatId(String chatId) {
        return accountRepository.findFirstByTelegramChatId(chatId);
    }

    public PlatformUser save(PlatformUser platformUser) {
        return register(platformUser);
    }

    public PlatformUser generateAccount(String login, String chatId) {
        String password = passwordGeneratorService.generateStrongPassword();
        List<Authority> roles = Collections.singletonList(authorityService.getAuthorityByRole(Role.USER));
        PersonalData personalData = new PersonalData();

        PlatformUser account = new PlatformUser(null, login, password, chatId, roles, personalData);
        register(account);

        return account;
    }

    private PlatformUser register(PlatformUser studentAccount) {
        String login = studentAccount.getLogin();

        PlatformUser account = new PlatformUser(null,
                login,
                passwordEncoder.encode(studentAccount.getPassword()),
                studentAccount.getTelegramChatId(),
                studentAccount.getAuthorities(),
                new PersonalData()
        );

        if (accountRepository.findFirstByLogin(login) == null) {
            PlatformUser user = accountRepository.save(account);
            log.info("Пользователь с логином {} был успешно создан", login);
            return user;
        } else {
            throw new UserAlreadyExistException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    public ResponseEntity<String> updateToAdmin(Long id) {
        PlatformUser student = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id " + id + " не найден"));

        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);
        List<Authority> studentAuthorities = student.getAuthorities();
        studentAuthorities.add(adminAuthority);

        accountRepository.save(student);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public List<PlatformUser> getAllStudents() {
        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);

        return accountRepository.findByAuthoritiesNotContaining(adminAuthority);
    }


    public PlatformUser findPlatformUserByLogin(String login) {
        return accountRepository.findFirstByLogin(login);
    }
}
