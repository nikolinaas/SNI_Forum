package org.unibl.etf.ip.sni_projekat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.JWTService;
import org.unibl.etf.ip.sni_projekat.services.JWTUserDetailsService;

@Service
public class JWTUserDetailsServiceImpl implements JWTUserDetailsService {

    private final UserEntityRepository  userEntityRepository;
    private final ModelMapper modelMapper;

    public JWTUserDetailsServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public JWTUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Ne postoji korisnik.")),JWTUser.class);
    }
}
