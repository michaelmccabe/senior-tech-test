package com.school.auth.data;

import com.school.auth.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account, String> {
}
