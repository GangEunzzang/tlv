package ubivelox.tlv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.service.TLVService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TLVController {

    private final TLVService tlvService;


    @PostMapping("/parse")
    public List<TLV> parse(@RequestBody String tlvData) {
        System.out.println("tlvData = " + tlvData);
        List<TLV> tlvs = tlvService.parseTlv(tlvData);
        System.out.println("tlvs = " + tlvs);
        return tlvs;
    }
}