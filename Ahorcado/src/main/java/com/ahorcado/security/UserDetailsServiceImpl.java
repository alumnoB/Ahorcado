package com.ahorcado.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ahorcado.dao.UsuarioDAO;
import com.ahorcado.models.Usuario;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Override
	public UserDetails loadUserByUsername(String usuarios) throws UsernameNotFoundException {
		Usuario usuario = usuarioDAO.findByNombreIgnoreCase(usuarios).orElse(null);
		
		UserBuilder builder = null;
		
		System.out.println("PRUEBA"+usuario);
		
		
		if (usuario != null) {
			
			builder = User.withUsername(usuarios);
			
			builder.disabled(false);
			
			builder.password(usuario.getPassword());
			
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		} else 
			throw new UsernameNotFoundException("Usuario no encontrado");
				
		return builder.build();
		
	}

}
