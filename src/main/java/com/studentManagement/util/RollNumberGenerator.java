package com.studentManagement.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Calendar;
import java.util.UUID;

public class RollNumberGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        return UUID.randomUUID().toString().substring(0, 6)  + UUID.randomUUID().toString().substring(0, 4);
    }
}
