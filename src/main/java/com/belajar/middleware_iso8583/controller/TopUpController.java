package com.belajar.middleware_iso8583.controller;

import com.belajar.middleware_iso8583.request.TopUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TopUpController {

    private final QMUX qmux;

    @PostMapping("/topUp")
    public Map<String, String> topUp(@RequestBody TopUpRequest topUpRequest) {
        Map<String, String> hasil = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");

        try {
            ISOMsg msg = new ISOMsg("0200");
            msg.set(4, topUpRequest.getNilai().setScale(0).toString());
            msg.set(7, simpleDateFormat.format(new Date()));
            msg.set(11, "000123");

            String bit48 = topUpRequest.getMsisdn().substring(0, 4);
            bit48 += String.format("%1$" + 13 + "s", topUpRequest.getMsisdn().substring(4));
            log.info("Bit 48 : {}", bit48);

            msg.set(48, bit48);
            msg.set(63, "131001");

            ISOMsg isoResp = qmux.request(msg, 20 * 1000);

            if (isoResp == null) {
                // kirim reversal
                // kalau masih timeout, ulangi 2x lagi reversal

                hasil.put("success", "false");
                hasil.put("error", "timeout");
                return hasil;
            }

            String response = new String(isoResp.pack());

            hasil.put("success", "true");
            hasil.put("response_code", isoResp.getString(39));
            hasil.put("raw_message", response);
        } catch (ISOException e) {
            e.printStackTrace();
        }

        return hasil;
    }

}
