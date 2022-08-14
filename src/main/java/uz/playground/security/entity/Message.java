package uz.playground.security.entity;


import uz.playground.security.constant.Lang;
import uz.playground.security.constant.TableName;
import uz.playground.security.entity.audit.UserAudit;

import javax.persistence.*;

@Entity
@Table(name = TableName.MESSAGE)
public class Message extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(name = "key")
    private String key;

    @Column(name = "message")
    private String message;


    public Message(String key, Lang lang, String message){
        this.key = key;
        this.message = message;
        this.lang = lang;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", lang=" + lang +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
