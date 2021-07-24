package com.omikheev.testapp.exchange.exception;

/**
 * Custom exception for cases, when some entities are not found
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
