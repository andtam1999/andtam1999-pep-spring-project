package com.example.repository;

import java.util.List;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public Message getByMessageId(int messageId); // default GetById is deprecated
    public List<Message> findAllByPostedBy(int postedBy);
}
