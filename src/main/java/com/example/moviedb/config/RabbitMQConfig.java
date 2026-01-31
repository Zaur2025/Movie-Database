package com.example.moviedb.config;

import org.springframework.amqp.core.Queue;  // 1. Импорт класса Queue из Spring AMQP
import org.springframework.context.annotation.Bean;  // 2. Аннотация для создания бина
import org.springframework.context.annotation.Configuration;  // 3. Класс с конфигурацией

@Configuration  // 4. Говорим Spring: "Это класс с настройками, создай все бины отсюда"
public class RabbitMQConfig {

    // Создаём простую очередь
    @Bean  // 5. "Spring, создай этот объект и положи в свой контейнер (Application Context)"
    public Queue movieCreatedQueue() {
        // 6. Создаём объект Queue с именем "movie.created.queue"
        return new Queue("movie.created.queue", true);  // true = durable
        // Параметры:
        // - "movie.created.queue" → имя очереди (буквально как папка для сообщений)
        // - true → durable (сохраняется при перезапуске RabbitMQ)
        // - false → exclusive (только для этого соединения) - не указано, значит false
        // - false → autoDelete (удалить при отсутствии consumers) - не указано, значит false
    }

    @Bean
    public Queue movieDeletedQueue() {
        return new Queue("movie.deleted.queue", true);  // 7. Вторая очередь
    }

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue("user.registered.queue", true); // 8. Третья очередь
    }
}