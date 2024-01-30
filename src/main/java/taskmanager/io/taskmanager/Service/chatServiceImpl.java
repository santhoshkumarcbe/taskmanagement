package taskmanager.io.taskmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskmanager.io.taskmanager.Model.Chat;
import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Repository.chatRepository;
import taskmanager.io.taskmanager.Repository.userRepository;

@Service
public class chatServiceImpl implements chatService {

    @Autowired
    chatRepository chatRepository;

    @Autowired
    userRepository userRepository;

    @Override
    public String postChat(Chat chat) {
        try {
            User s = userRepository.findById(chat.getSenderId()).orElse(null);
            User r = userRepository.findById(chat.getReceiverId()).orElse(null);
            if (r != null && s != null) {
                chatRepository.save(chat);
                return "true";
            } else if (r == null && s == null) {
                return "Sender and Receiver not found";
            } else if (r != null && s == null) {
                return "Sender not found";
            } else if (r == null && s != null) {
                return "Receiver not found";
            } else {
                return "Unknown request";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public List<Chat> findAllChatBySenderIdAndReceiverId(int senderId, int receiverId) {
        try {
            return chatRepository.findChatsBySenderAndReceiver(senderId, receiverId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteChatById(int chatId) {
        Chat c = chatRepository.findById(chatId).orElse(null);
        if (c != null) {
            return "true";
        }
        return "chat not found";
    }

}
