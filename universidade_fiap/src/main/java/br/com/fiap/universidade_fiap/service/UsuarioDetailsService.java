package br.com.fiap.universidade_fiap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository repU;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repU.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String perfil = "ROLE_" + usuario.getPerfil().toString();

        return new User(
                usuario.getEmail(),
                usuario.getSenhaHash(),
                java.util.Arrays.asList(new SimpleGrantedAuthority(perfil))
        );
    }
}
