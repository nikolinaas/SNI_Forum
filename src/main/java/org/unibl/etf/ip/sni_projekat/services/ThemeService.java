package org.unibl.etf.ip.sni_projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.unibl.etf.ip.sni_projekat.model.Theme;
import org.unibl.etf.ip.sni_projekat.repositories.ThemeEntityRepository;

import java.util.List;

public interface ThemeService {

    public List<Theme>  getAll();

}
