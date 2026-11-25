package cl.duoc.notificaciones.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class SqsProducerService {

    private static final Logger logger =
            LoggerFactory.getLogger(SqsProducerService.class);

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue-url:}")
    private String queueUrl;

    public SqsProducerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public String enviarMensaje(String mensaje) {

        if (queueUrl == null || queueUrl.isBlank()) {
            throw new IllegalStateException(
                    "La URL de la cola SQS no esta configurada");
        }

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(mensaje)
                .build();

        SendMessageResponse response = sqsClient.sendMessage(request);

        logger.info(
                "Mensaje enviado correctamente a SQS. MessageId: {}",
                response.messageId()
        );

        return response.messageId();
    }
}