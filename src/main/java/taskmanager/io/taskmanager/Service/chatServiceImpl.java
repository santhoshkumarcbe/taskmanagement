package taskmanager.io.taskmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskmanager.io.taskmanager.Model.Chat;
import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Repository.chatRepository;
import taskmanager.io.taskmanager.Repository.userRepository;

@Service
public class chatServiceImpl implements chatService{

    @Autowired
    chatRepository chatRepository;

    @Autowired
    userRepository userRepository;

    @Override
    public String postChat(Chat chat) {
        try {
            User m = userRepository.findById(chat.getManagerId()).orElse(null);
            User c = userRepository.findById(chat.getContributorId()).orElse(null);
            if (c!=null && m!=null) {
                chatRepository.save(chat);
                return "true";  
            }
            else if(c==null && m == null){
                return "Manager and Contributor not found";
            }
            else if(c!=null && m==null){
                return "Manager not found";
            }
            else if(c==null && m!=null){
                return "Contributor not found";
            }
            else{
                return "Unknown request";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public List<Chat> findAllChatByManagerIdAndContributorId(int managerId, int contributorId) {
        try {
            return chatRepository.findAllChatByManagerIdAndContributorId(managerId,contributorId);
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
