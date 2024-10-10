package io.ssafy.p.j11a307.push_alert.dto.alerts;

import java.util.Map;

public interface FcmAlertData {

    String getMessage();

    String getTitle();

    String getLink();

    Map<String, String> getData();
}
