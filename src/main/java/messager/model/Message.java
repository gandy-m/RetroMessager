package messager.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class Message {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;


   private String text;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "sender_username")
   private User sender;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "recipient_username")
   private User recipient;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "chat_id")
   private Chat chat;


   @DateTimeFormat(pattern = "MM-dd")
   @JsonFormat(pattern = "MM-dd")
   private LocalDate date;


   @DateTimeFormat(pattern = "HH:mm")
   @JsonFormat(pattern = "HH:mm")
   private LocalTime time;


   public Message(String text, User sender, User recipient, Chat chat) {
      this.text = text;
      this.sender = sender;
      this.recipient = recipient;
      this.chat = chat;
      date = LocalDate.now();
      time = LocalTime.now();
   }


}
