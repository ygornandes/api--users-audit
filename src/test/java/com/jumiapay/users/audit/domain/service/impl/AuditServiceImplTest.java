package com.jumiapay.users.audit.domain.service.impl;

import com.jumiapay.users.audit.application.config.properties.messages.MessagesProperties;
import com.jumiapay.users.audit.application.exceptions.AuditNotFoundException;
import com.jumiapay.users.audit.domain.handler.PayloadHandler;
import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.repository.AuditRepository;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;
import com.jumiapay.users.audit.resource.queue.ProducerQueue;
import com.jumiapay.users.audit.utils.MockUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceImplTest {

    private AuditServiceImpl service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ProducerQueue producer;

    @Mock
    private AuditRepository repository;

    @Before
    public void setUp(){
        MessagesProperties messages = new MessagesProperties();
        service = new AuditServiceImpl(repository,producer,messages,new PayloadHandler());
    }

    @Test
    public void shouldCreateAuditWithSuccess() throws IOException {
        service.create(MockUtil.getAudit("Arnold","CUSTOMER"));
        verify(repository,atLeastOnce()).create(any());
    }

    @Test
    public void shouldEnqueueValidPayloadWithSuccess() throws IOException {
      service.enqueue(MockUtil.getAuditValidPayload("Arnold","CUSTOMER"));
      verify(producer,atLeastOnce()).send(any());
    }

    @Test
    public void shouldEnqueueWithSuccessValidPayloadWithSentitiveValues() throws IOException {
        service.enqueue(MockUtil.getAuditValidPayloadWithSentitiveValues("Bob","CUSTOMER"));
        verify(producer,atLeastOnce()).send(any());
    }

    @Test
    public void shouldReturnAuditByUserIdWIthSuccess(){

        when(repository.getAuditsByUserId(anyString(),anyInt(),anyInt(),any(),any())).thenReturn(MockUtil.getOptionalAudits());
        List<Audit> audits = service.getAuditsByUserId("George", 0, 10, new String[]{"id"}, DirectionEnum.ASC);

        assertThat(audits.isEmpty(), is(false));
        assertThat(audits.size(),is(3));
        assertThat(audits.get(0).getUser().getId(),is("George"));

        verify(repository,atLeastOnce()).getAuditsByUserId(anyString(),anyInt(),anyInt(),any(),any());

    }

    @Test
    public void shouldRetunrnAuditNotFoundExceptionInGetByUserId(){
        thrown.expect(AuditNotFoundException.class);
        when(repository.getAuditsByUserId(anyString(),anyInt(),anyInt(),any(),any())).thenReturn(Optional.empty());
        service.getAuditsByUserId("George", 0, 10, new String[]{"id"}, DirectionEnum.ASC);
        verify(repository,atLeastOnce()).getAuditsByUserId(anyString(),anyInt(),anyInt(),any(),any());
    }


    @Test
    public void shouldReturnAuditsWithSuccess(){

        when(repository.getAudits(anyInt(),anyInt(),any(),any())).thenReturn(MockUtil.getOptionalAudits());
        List<Audit> audits = service.getAudits( 0, 10, new String[]{"id"}, DirectionEnum.ASC);

        assertThat(audits.isEmpty(), is(false));
        assertThat(audits.size(),is(3));
        assertThat(audits.get(0).getUser().getId(),is("George"));

        verify(repository,atLeastOnce()).getAudits(anyInt(),anyInt(),any(),any());

    }

    @Test
    public void shouldRetunrnAuditNotFoundException(){
        thrown.expect(AuditNotFoundException.class);
        when(repository.getAudits(anyInt(),anyInt(),any(),any())).thenReturn(Optional.empty());
        service.getAudits( 0, 10, new String[]{"id"}, DirectionEnum.ASC);
        verify(repository,atLeastOnce()).getAudits(anyInt(),anyInt(),any(),any());
    }
}