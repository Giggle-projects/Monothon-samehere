package com.giggle.samehere;

import com.giggle.samehere.group.domain.Group;
import com.giggle.samehere.group.domain.GroupRepository;
import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SameHereApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SameHereApplication.class, args);

        TestDummy bean = context.getBean(TestDummy.class);
        bean.setUp();
    }
}

@Component
class TestDummy {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ItemRepository itemRepository;

    public void setUp() {
        groupRepository.save(new Group("name", "password"));
        groupRepository.save(new Group("name", "password"));

        itemRepository.save(Item.multipleChoicesQuestion("MBTI", Arrays.asList("INTP", "ENFP", "ESTJ", "ENFJ")));
        itemRepository.save(Item.shortAnswerQuestion("더미"));
        itemRepository.save(Item.shortAnswerQuestion("한줄소개"));
        itemRepository.save(Item.multipleChoicesQuestion("좋아하는계절", Arrays.asList("겨울", "봄", "여름", "가을")));
        itemRepository.save(Item.multipleChoicesQuestion("좋아하는음식", Arrays.asList("한식", "중식", "일식", "양식")));
        itemRepository.save(Item.shortAnswerQuestion("생년월일"));
        itemRepository.save(Item.shortAnswerQuestion("깃허브"));
        itemRepository.save(Item.shortAnswerQuestion("링크드인"));
    }
}