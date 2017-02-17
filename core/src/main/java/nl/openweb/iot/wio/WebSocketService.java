package nl.openweb.iot.wio;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import lombok.Data;
import nl.openweb.iot.wio.domain.Node;

@Service
public class WebSocketService {

    private static final Object PLACE_HOLDER_OBJECT = new Object();
    private WioSettings wioSettings;
    private ObjectMapper objectMapper;
    private TaskScheduler taskScheduler;
    private Map<String, Object> registry = new ConcurrentHashMap<>();

    public WebSocketService(WioSettings wioSettings, ObjectMapper objectMapper, TaskScheduler taskScheduler) {
        this.wioSettings = wioSettings;
        this.objectMapper = objectMapper;
        this.taskScheduler = taskScheduler;
    }

    public void connect(Node node) {
        if (!registry.containsKey(node.getNodeSn())) {
            synchronized (this) {
                if (!registry.containsKey(node.getNodeSn())) {
                    StandardWebSocketClient websocket = new StandardWebSocketClient();
                    websocket.doHandshake(new WioWebSocketHandler(node), wioSettings.getBaseUrl().replaceFirst("http(s)?://", "wss://") + "/v1/node/event");
                    registry.put(node.getNodeSn(), PLACE_HOLDER_OBJECT);
                }
            }
        }

    }

    private void reconnect(Node node) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                connect(node);
            }
        }, calendar.getTime());
    }

    public class WioWebSocketHandler extends AbstractWebSocketHandler implements WebSocketHandler {

        public final Node node;

        WioWebSocketHandler(Node node) {
            this.node = node;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            session.sendMessage(new TextMessage(node.getNodeKey()));
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            MessageBean messageBean = objectMapper.readValue(message.getPayload(), MessageBean.class);
            if (StringUtils.isBlank(messageBean.getError())) {
                node.event(messageBean.getMessage());
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            reconnect(node);
        }
    }


    @Data
    private static class MessageBean {
        @JsonProperty("msg")
        private Map<String, String> message;
        private String error;
    }

}
