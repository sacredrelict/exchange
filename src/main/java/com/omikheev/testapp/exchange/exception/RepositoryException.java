package com.omikheev.testapp.exchange.exception;

/**
 * Custom exception for cases, when some entity save/update operations are failed
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public class RepositoryException extends RuntimeException {

    public RepositoryException(String msg) {
        super(msg);
    }
}
