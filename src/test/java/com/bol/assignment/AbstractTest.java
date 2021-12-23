package com.bol.assignment;


import com.bol.assignment.component.GameEmitterRepository;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.service.MoveRuleService;
import com.bol.assignment.service.multi.MultiKalahService;
import com.bol.assignment.service.multi.NotificationService;
import com.bol.assignment.service.single.SingleKalahService;
import org.junit.ClassRule;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(
        properties = "spring.mvc.async.request-timeout= 5000")
public class AbstractTest {


    @Container
    @ClassRule
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2")
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl());
    }


    @Autowired
    public TestRestTemplate testRestTemplate;

    @SpyBean
    public RestTemplate restTemplate;

    @Autowired
    public MoveRuleService ruleService;

    @Autowired
    public BoardRepository boardRepository;

    @Autowired
    public MoveRuleService moveRuleService;

    @Autowired
    public GameEmitterRepository gameEmitterRepository;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public MultiKalahService kalahService;

    @Autowired
    public SingleKalahService singleKalahService;


}
