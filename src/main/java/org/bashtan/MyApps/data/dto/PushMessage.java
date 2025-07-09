package org.bashtan.MyApps.data.dto;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PushMessage {
    private String token;
    private String title;
    private String body;
    private Map<String, String> data;
}

