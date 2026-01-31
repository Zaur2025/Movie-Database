package com.example.moviedb.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;  // 1. Импорт аннотации слушателя
import org.springframework.stereotype.Component;  // 2. Делаем класс Spring компонентом

@Component  // 3. "Spring, управляй этим классом, это часть приложения"
public class MovieEventConsumer {

    // Слушаем очередь "movie.created.queue"
    @RabbitListener(queues = "movie.created.queue")  // 4. КЛЮЧЕВАЯ АННОТАЦИЯ!
    // ↑ Spring: "Слушай очередь 'movie.created.queue' и когда придёт сообщение,
    //           вызывай этот метод handleMovieCreated()"
    public void handleMovieCreated(String message) {  // 5. message - то, что отправил Producer
        System.out.println("🎬 [RABBITMQ] Получено сообщение: " + message);
        System.out.println("   📧 Имитация отправки email админу...");

        // Имитация долгой операции (2 секунды)
        try {
            Thread.sleep(2000);  // 6. Имитация реальной работы (отправка email)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 7. Правильная обработка прерывания
        }

        System.out.println("   ✅ Email отправлен (эмуляция)");
        System.out.println("   📊 Имитация записи в лог аналитики...");
        System.out.println("   ✅ Аналитика записана");
        // 8. После выполнения метода RabbitMQ автоматически подтвердит получение
        //    (autoAck = true по умолчанию)
    }

    // Слушаем очередь "movie.deleted.queue"
    @RabbitListener(queues = "movie.deleted.queue")  // 9. Второй слушатель для другой очереди
    public void handleMovieDeleted(String message) {
        System.out.println("🗑️ [RABBITMQ] Получено сообщение: " + message);
        System.out.println("   🔄 Имитация очистки кеша...");

        try {
            Thread.sleep(1000);  // 10. Имитация очистки кеша
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("   ✅ Кеш очищен");
    }

    // Слушаем очередь "user.registered.queue"
    @RabbitListener(queues = "user.registered.queue")
    // ↑ Spring: "Слушай очередь 'movie.created.queue' и когда придёт сообщение,
    //           вызывай этот метод handleUserRegistered()"
    public void handleUserRegistered(String message) {
        System.out.println("🎬 [RABBITMQ] Получено сообщение: " + message);
        System.out.println("   📧 Имитация отправки email админу о создании пользователя...");
    }
}