package ubivelox.tlv.util;

import ubivelox.tlv.common.exception.BaseException;
import ubivelox.tlv.common.exception.ResultCode;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.domain.Tag;
import ubivelox.tlv.domain.type.TagType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class TLVParser {

    public static List<TLV> parse(String tlvString) {
        byte[] data = hexStringToByteArray(tlvString);

        List<TLV> tlvList = new ArrayList<>();
        TLV parent = null;

        int offset = 0;
        while (offset < data.length) {
            Tag tag = readTag(data, offset);
            offset += tag.getValue().length() / 2;

            int length = readLength(data, offset);
            offset++;

            if (tag.getType() == TagType.CONSTRUCTED) {
                TLV tlv = TLV.of(tag, length, parent);
                parent = tlv;
                tlvList.add(tlv);
                continue;
            }

            String value = readValue(data, offset, length);
            TLV tlv = TLV.of(tag, length, value, parent);
            tlvList.add(tlv);
            offset += length;

            if (parent != null && offset >= parent.getLength()) {
                parent = parent.getParent();
            }

        }

        return tlvList;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();

        if (len % 2 != 0) {
            throw new BaseException(ResultCode.INVALID_TLV);
        }

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int high = Character.digit(s.charAt(i), 16);
            int low = Character.digit(s.charAt(i + 1), 16);

            if (high == -1 || low == -1) {
                throw new BaseException(ResultCode.INVALID_TLV);
            }

            data[i / 2] = (byte) ((high << 4) + low);
        }

        return data;
    }


    private static Tag readTag(byte[] data, int offset) {
        StringBuilder tagValue = new StringBuilder();
        tagValue.append(String.format("%02X", data[offset] & 0xFF));
        TagType type = TagType.fromByte(data[offset]);

        if ((data[offset] & 0x1F) == 0x1F) {
            do {
                offset++;
                if (offset >= data.length) {
                    throw new BaseException(ResultCode.INVALID_TAG, "Tag parsing exceeds data length");
                }
                tagValue.append(String.format("%02X", data[offset] & 0xFF));
            } while ((data[offset] & 0x80) == 0x80);
        }

        return Tag.create(tagValue.toString(), type);
    }

    private static int readLength(byte[] data, int offset) {
        int firstByte = data[offset] & 0xFF;
        if (firstByte < 0x80) {
            return firstByte;
        }

        int numBytes = firstByte - 0x80;
        if (offset + numBytes >= data.length) {
            throw new BaseException(ResultCode.INVALID_LENGTH, "Length bytes exceed data length");
        }

        int length = 0;
        for (int i = 0; i < numBytes; i++) {
            length = (length << 8) | (data[offset + 1 + i] & 0xFF);
        }

        return length;
    }

    private static String readValue(byte[] data, int offset, int length) {
        if (offset + length > data.length) {
            throw new BaseException(ResultCode.INVALID_VALUE, "Value bytes exceed data length");
        }

        return IntStream.range(offset, offset + length)
                .mapToObj(i -> String.valueOf(data[i]))
                .reduce("", String::concat);
    }


}
