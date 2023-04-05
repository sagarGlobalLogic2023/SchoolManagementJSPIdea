package com.studentManagement.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;


public class SessionIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        return "Session" + "-" + UUID.randomUUID().toString().substring(0, 6) + "-" + UUID.randomUUID().toString().substring(0, 6);
    }
}
