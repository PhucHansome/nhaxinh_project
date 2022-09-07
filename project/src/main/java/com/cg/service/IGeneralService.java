package com.cg.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    List<T> findAll();

    Boolean existById(Long id);

    Optional<T> findById(Long id);

    T getById(Long id);

    T save(T t) throws MessagingException, UnsupportedEncodingException;

    void remove(Long id);

    void softDelete(T t);
}
