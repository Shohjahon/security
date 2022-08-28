package uz.playground.security.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.playground.security.dto.MessageDto;
import uz.playground.security.entity.Message;
import uz.playground.security.service.MessageService;

import static uz.playground.security.dto.ResponseData.response;

@RestController
@RequestMapping(value = "/api/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMessage(@RequestBody MessageDto messageDto){
        return messageService.createMessage(messageDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editMessage(@RequestBody Message message){
        return messageService.editMessage(message);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<?> deletemessage(@PathVariable Long messageId){
        return messageService.deleteMessage(messageId);
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<?> getMessageByKey(@PathVariable String key){
        return response(messageService.getMessage(key));
    }
}
