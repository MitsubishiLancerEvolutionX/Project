package com.override.service;

import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.repositories.PersonalDataRepository;
import com.override.repositories.PlatformUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData personalData, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData data = user.getPersonalData();
        personalData.setId(data.getId());
        personalData.setUser(user);
        personalDataRepository.save(personalData);
        user.setPersonalData(personalData);
        platformUserRepository.save(user);
    }
}
