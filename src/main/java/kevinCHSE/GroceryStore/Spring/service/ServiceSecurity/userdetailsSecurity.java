package kevinCHSE.GroceryStore.Spring.service.ServiceSecurity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kevinCHSE.GroceryStore.Spring.entities.Login.User;
import kevinCHSE.GroceryStore.Spring.repositories.LoginRepository.UserRepository;



@Service 
public class userdetailsSecurity implements UserDetailsService {

    private final UserRepository repository;

    public userdetailsSecurity(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User>userOptional=repository.findById(id);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("No se encontro el id:"+id);
        }


        User user=userOptional.orElseThrow();

        List<GrantedAuthority>roles= user.getRoles().stream()
        .map(role->new SimpleGrantedAuthority(role.getRole()))
        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getId(),
        user.getPassword(),
        user.isEnable(),
        true,
        true,
        true,
        roles);
    }

    

    


}