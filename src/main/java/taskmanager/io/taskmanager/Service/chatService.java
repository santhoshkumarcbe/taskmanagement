package taskmanager.io.taskmanager.Service;

import java.util.List;

import taskmanager.io.taskmanager.Model.Chat;

public interface chatService {

    String postChat(Chat chat);

    List<Chat> findAllChatBySenderIdAndReceiverId(int managerId, int contributorId);

    String deleteChatById(int chatId);

    
}
