package org.camunda.bpm.autotest.exception;

/**
 * Created by Beris on 23/6/2562.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
