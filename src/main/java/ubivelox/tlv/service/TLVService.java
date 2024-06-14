package ubivelox.tlv.service;

import org.springframework.stereotype.Service;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.util.TLVParser;

import java.util.List;

@Service
public class TLVService {

    public List<TLV> parseTlv(String tlvString) {
        return TLVParser.parse(tlvString);
    }

}
