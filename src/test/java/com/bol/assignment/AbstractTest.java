package com.bol.assignment;


import com.bol.assignment.component.GameEmitterRepository;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.service.KalahService;
import com.bol.assignment.service.MoveRuleService;
import com.bol.assignment.service.NotificationService;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractTest {

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
    public KalahService kalahService;


}
