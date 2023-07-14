package com.okoho.okoho.service.impl;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.okoho.okoho.domain.Candidat;
import com.okoho.okoho.domain.UserAccount;
import com.okoho.okoho.repository.UserAccountRepository;
import com.okoho.okoho.rest.errors.BadRequestAlertException;
import com.okoho.okoho.rest.errors.EmailAlreadyUsedException;
import com.okoho.okoho.rest.errors.TypeAccountExeption;
import com.okoho.okoho.service.CandidatService;
import com.okoho.okoho.service.RecruteurService;
import com.okoho.okoho.service.UserAccountService;
import com.okoho.okoho.service.dto.CandidatDTO;
import com.okoho.okoho.service.dto.RecruteurDTO;
import com.okoho.okoho.service.dto.RegisterRequest;
import com.okoho.okoho.service.dto.UserAccountDTO;

/**
 * Service Implementation for managing {@link UserAccount}.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserAccountRepository userAccountRepository;
    private final CandidatService candidatService;
    private final RecruteurService recruteurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, CandidatService candidatService, RecruteurService recruteurService) {
        this.userAccountRepository = userAccountRepository;
        this.candidatService = candidatService;
        this.recruteurService = recruteurService;
    }

    @Override
    public UserAccount register(RegisterRequest registerRequest) {
        if(registerRequest.getUser_type()==null){
            //throw new TypeAccountExeption();
        }
        userAccountRepository
        .findOneByEmailIgnoreCase(registerRequest.getEmail())
        .ifPresent(
            existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            }
        );
            UserAccount account = new UserAccount();
            account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            account.setEmail(registerRequest.getEmail());
            account.setFirstName(registerRequest.getFirstName());
            account.setLastName(registerRequest.getLastName());
            account.setImageUrl(registerRequest.getImageUrl());
            userAccountRepository.save(account);
            if (registerRequest.getLangKey() == null) {
                account.setLangKey("de"); // default language
            } else {
                account.setLangKey(registerRequest.getLangKey());
            }
            if(registerRequest.getUser_type()==1){
                var candidat=new Candidat();
                candidat.setCountry(registerRequest.getCountry());
                candidat.setDob(registerRequest.getDob());
                candidat.setUserAccount(account);
                candidatService.save(candidat);
            }else if(registerRequest.getUser_type()==2){
                var recruteur=new RecruteurDTO();
                recruteur.setCountry(registerRequest.getCountry());
                recruteur.setDob(registerRequest.getDob());
                recruteur.setBp(registerRequest.getBp());
                recruteur.setEntreprise(registerRequest.getEntreprise());
                recruteur.setUserAccount(account);
                recruteurService.save(recruteur);
            }else{
                
            }
            return account;
    }
  
    @Override
    @Transactional
    public UserAccountDTO save(UserAccountDTO userAccountDTO) {
       
        userAccountRepository
        .findOneByEmailIgnoreCase(userAccountDTO.getEmail())
        .ifPresent(
            existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            }
        );
            UserAccount account = new UserAccount();
            account.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
            account.setEmail(userAccountDTO.getEmail());
            account.setFirstName(userAccountDTO.getFirstName());
            account.setLastName(userAccountDTO.getLastName());
            account.setImageUrl(userAccountDTO.getImageUrl());
            if (userAccountDTO.getLangKey() == null) {
                account.setLangKey("de"); // default language
            } else {
                account.setLangKey(userAccountDTO.getLangKey());
            }
           return userAccountDTO;
    }
    private boolean removeNonActivatedUser(UserAccount existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userAccountRepository.delete(existingUser);
        this.clearUserCaches(existingUser);
        return true;
    }

    @Override
    public Optional<UserAccountDTO> partialUpdate(UserAccountDTO userAccountDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partialUpdate'");
    }


    @Override
    public List<UserAccountDTO> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }


    @Override
    public Optional<UserAccountDTO> findOne(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }


    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    @Override
    public Object resetpassword(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetpassword'");
    }


    @Override
    public boolean isEnabledUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEnabledUser'");
    }
    private void clearUserCaches(UserAccount user) {
        
    }



}
