package uz.playground.security.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.MessageKey;
import uz.playground.security.dto.MessageDto;
import uz.playground.security.entity.Message;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.repository.MessageRepository;
import uz.playground.security.security.UserPrincipal;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static uz.playground.security.constant.Lang.UZ;
import static uz.playground.security.helper.SecurityHelper.getUser;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ResponseHelper responseHelper;

    public MessageService(MessageRepository messageRepository,
                          ResponseHelper responseHelper) {
        this.messageRepository = messageRepository;
        this.responseHelper = responseHelper;
    }

    @PostConstruct
    public void initialize(){
        if (messageRepository.findAll(Pageable.ofSize(10)).isEmpty()){
            List<Message> messageList = new LinkedList<>();

            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.EN, "Invalid Token"));
            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.UZ, "Noto'g'ri kalit"));
            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.RU, "Не верный ключ"));

            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.EN, "User does not exist"));
            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.RU, "Пользователь не существует"));
            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.UZ, "Foydalanuvchi mavjud emas"));

            messageList.add(new Message(MessageKey.INCORRECT_PASSWORD, Lang.EN, "Incorrect Password"));
            messageList.add(new Message(MessageKey.INCORRECT_PASSWORD, Lang.UZ, "Parol noto'g'ri"));
            messageList.add(new Message(MessageKey.INCORRECT_PASSWORD, Lang.RU, "Неправильный пароль"));

            messageList.add(new Message(MessageKey.SUCCESS, Lang.EN, "Success"));
            messageList.add(new Message(MessageKey.SUCCESS, Lang.UZ, "Muvaffaqiyatli"));
            messageList.add(new Message(MessageKey.SUCCESS, Lang.RU, "Успешно"));

            messageList.add(new Message(MessageKey.ERROR, Lang.EN, "Error"));
            messageList.add(new Message(MessageKey.ERROR, Lang.UZ, "Hatolik"));
            messageList.add(new Message(MessageKey.ERROR, Lang.RU, "Ошибка"));

            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.EN, "Data not Found"));
            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.UZ, "Ma'lumotlar topilmai"));
            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.RU, "Данные не найдены"));

            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.EN, "Internal server error, Please contact your administrator."));
            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.UZ, "Tizimning ichki xatoligi"));
            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.RU, "Внутренняя ошибка сервера"));

            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.EN, "Invalid data"));
            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.UZ, "Noto'g'ri ma'lumotlar"));
            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.RU, "Неправильные данные"));

            messageList.add(new Message(MessageKey.USERNAME_EXISTS, Lang.EN, "Username is already taken!"));
            messageList.add(new Message(MessageKey.USERNAME_EXISTS, Lang.UZ, "Username is already taken!"));
            messageList.add(new Message(MessageKey.USERNAME_EXISTS, Lang.RU, "Username is already taken!"));

            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.EN, "Email is already taken!"));
            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.UZ, "Email is already taken!"));
            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.RU, "Email is already taken!"));

            messageList.add(new Message("valid.not.blank.signup.name", Lang.EN, "Full name is empty!"));
            messageList.add(new Message("valid.not.blank.signup.name", Lang.UZ, "Ism sharifingizni kiriting!"));
            messageList.add(new Message("valid.not.blank.signup.name", Lang.RU, "Full name is empty!"));

            messageList.add(new Message("valid.not.blank.signup.username", Lang.EN, "Username is required"));
            messageList.add(new Message("valid.not.blank.signup.username", Lang.UZ, "Foydalanuvchi nomini kiriting"));
            messageList.add(new Message("valid.not.blank.signup.username", Lang.RU, "Username is required"));

            messageList.add(new Message("valid.email.signup.email", Lang.EN, "Email is not valid"));
            messageList.add(new Message("valid.email.signup.email", Lang.UZ, "Email manzil noto'g'ri"));
            messageList.add(new Message("valid.email.signup.email", Lang.RU, "Email is not valid"));

            messageList.add(new Message("valid.password.signup.password", Lang.EN, "Password is not valid!"));
            messageList.add(new Message("valid.password.signup.password", Lang.UZ, "Parol noto'g'ri kiritilgan"));
            messageList.add(new Message("valid.password.signup.password", Lang.RU, "Password is not valid"));

            messageRepository.saveAll(messageList);
        }
    }

    public String getMessage(String key){
        UserPrincipal user = getUser();
        final var lang = Optional.ofNullable(user).map(UserPrincipal::getLang).orElse(UZ);
        return messageRepository.findByKeyAndLang(key, lang)
                .map(Message::getMessage)
                .orElse(key);
    }

    public ResponseEntity<?> createMessage(MessageDto messageDto){
        Optional<Message> messageOpt = messageRepository.findByKeyAndLang(messageDto.getKey(), messageDto.getLang());
        if (messageOpt.isPresent()){
            return responseHelper.invalidData();
        }
        Message message = new Message(messageDto.getKey(), messageDto.getLang(), messageDto.getMessage());
        messageRepository.save(message);
        return responseHelper.success();
    }

    public ResponseEntity<?> editMessage(Message message){
        Optional<Message> messageOpt = messageRepository.findByKeyAndLang(message.getKey(), message.getLang());
        if (messageOpt.isEmpty()){
            return responseHelper.noDataFound();
        }
        Message msg = messageOpt.get();
        msg.setKey(message.getKey());
        msg.setLang(message.getLang());
        msg.setMessage(message.getMessage());
        messageRepository.save(msg);
        return responseHelper.success();
    }

    public ResponseEntity<?> deleteMessage(Long messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isEmpty()){
            return responseHelper.noDataFound();
        }
        messageRepository.delete(message.get());
        return responseHelper.success();
    }
}
