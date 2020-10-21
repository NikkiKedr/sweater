package com.example.sweater;

import com.example.sweater.domane.Message;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


//Переход по страницам
@Controller
public class GreetingController {
//Импорт репозитория сообщений
    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    //Возвращаем список сообщений
    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }
    //Выдергиваем параметры из main.mustache
    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        //Сохранение нового сообщения
        Message message = new Message(text,tag);
        messageRepository.save(message);

        //Взяли репозиторий и положили модель
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    //Создание фильтра по тегу
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;
        if (filter!=null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
model.put("messages", messages);
return "main";
    }
}
