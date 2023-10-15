package com.example.solumonbackend.chat.repository;

import com.example.solumonbackend.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> , ChatMessageRepositoryCustom {

}
