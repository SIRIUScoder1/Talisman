package com.talisman.dao;

import com.talisman.entity.UserAccount;

public interface AccountDAO {

    public UserAccount findAccount(final String userName);
}
