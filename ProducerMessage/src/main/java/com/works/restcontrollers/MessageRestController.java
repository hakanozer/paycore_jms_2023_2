package com.works.restcontrollers;

import com.works.models.DataMessage;
import com.works.services.DataMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

    final DataMessageService messageService;

    @PostMapping("/send")
    public ResponseEntity send(@RequestBody DataMessage dataMessage) {
        return messageService.send(dataMessage);
    }
}
