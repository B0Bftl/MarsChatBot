package Game;

import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    //TODO MARS_URL als property
    //TODO npe bei path var catchen
    //TODO nur jar übertragen
    //TODO headless als property

    //TODO echter ping
    //TODO final greenery
    //TODO initial buy

    private static final String MARS_URL = "http://168.119.225.172:8080/api/player?id=f814f3933fc2";

    private static JSONObject lastJson;

    public static void main(String [ ] args) {
        System.out.println("Starting Chatbot");
        WebController.connectToWhatsapp();

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
            () -> {
                System.out.println(MARS_URL);
                JSONObject currentJson = null;
                try {
                    currentJson = WebController.readMarsJson(MARS_URL);
                    if (lastJson == null || lastJson.isEmpty()) {
                        System.out.println("First call to Mars");
                    } else {
                        System.out.println("Phase: " + JsonEvaluator.getPhase(currentJson) + ",  lastActivePlayers: " + JsonEvaluator.getActivePlayers(lastJson) + " currentPlayers: " + JsonEvaluator.getActivePlayers(currentJson));
                        JsonEvaluator.evaluateSendingMessage(lastJson, currentJson);
                    }
                    lastJson = currentJson;
                } catch (Exception e) {
                    System.out.println("lastJson: " + lastJson);
                    System.out.println("currentJson" + currentJson);
                    System.out.println(e.getMessage());
                }
            },
            50,
            1500,
            TimeUnit.MILLISECONDS
        );
    }
}
