package jms.dan.usuarios.service;

import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Override
    public Boolean checkClientCreditSituation(String clientCuit) {
        return true;
    }
}
