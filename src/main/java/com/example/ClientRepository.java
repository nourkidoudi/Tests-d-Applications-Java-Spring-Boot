package com.example;

import java.util.Optional;

public interface ClientRepository {

    Optional<Client> findById(Long clientId);
}
