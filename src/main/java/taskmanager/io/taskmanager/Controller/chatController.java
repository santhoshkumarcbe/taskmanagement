package taskmanager.io.taskmanager.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import taskmanager.io.taskmanager.Model.Chat;
import taskmanager.io.taskmanager.Service.chatService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController

@RequestMapping("/chat")
public class chatController {
    @Autowired
    chatService chatService;

    @PostMapping("/post")
    public ResponseEntity<Chat> postChat(@RequestBody Chat chat) {
        try {
            if (chatService.postChat(chat) == "true") {
                return new ResponseEntity<>(chat, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getallchats")
    public ResponseEntity<List<Chat>> findAllChatBySenderIdAndReceiverId(@RequestParam int senderId,
            @RequestParam int receiverId) {
        try { 
            System.out.println("get all chats");

            List<Chat> chats = chatService.findAllChatBySenderIdAndReceiverId(senderId, receiverId);
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{chatId}")
    public ResponseEntity<String> deleteChatById(@PathVariable("chatId") int chatId) {
        try {
            String message = chatService.deleteChatById(chatId);
            if (message == "true") {
                return new ResponseEntity<>("Chat deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(message, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
