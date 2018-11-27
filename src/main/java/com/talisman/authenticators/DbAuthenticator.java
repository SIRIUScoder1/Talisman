package com.talisman.authenticators;

import com.talisman.dao.AccountDAO;
import com.talisman.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbAuthenticator implements UserDetailsService {

    private AccountDAO accountDAO;

    @Autowired
    public DbAuthenticator(final AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {

        UserAccount userAccount = accountDAO.findAccount(userName);
        System.out.println("debug userAccount= " + userAccount);

        if(userAccount == null) {
            throw new UsernameNotFoundException("User " + userName + " is not found in database");
        }
        String role = userAccount.getUserRole();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        grantedAuthorityList.add(authority);

        boolean accountActive = userAccount.getActive();
        boolean accountNotExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNotLocked = true;

        UserDetails userDetails = (UserDetails) new User(userAccount.getUserName(), userAccount.getPassword(), accountActive,
                accountNotExpired, credentialsNotExpired, accountNotLocked,
                grantedAuthorityList);
        return userDetails;
    }
}
