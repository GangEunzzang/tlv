//package ubivelox.tlv;
//
//import ubivelox.tlv.domain.TLV;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BERTLVParser {
//
//
//    public static List<TLV> parse(byte[] data) {
//        List<TLV> tlvList = new ArrayList<>();
//        int offset = 0;
//        while (offset < data.length) {
//            TLV tlv = parseTLV(data, offset);
//            tlvList.add(tlv);
//            offset += tlv.getLength() + calculateTLVHeaderLength(tlv);
//        }
//        return tlvList;
//    }
//
//    private static TLV parseTLV(byte[] data, int offset) {
//        if (offset >= data.length) {
//            throw new IllegalArgumentException("Offset exceeds available data length");
//        }
//
//        String tag = readTag(data, offset);
//        offset += tag.length() / 2;
//        int length = readLength(data, offset);
//        int lengthBytes = calculateLengthBytes(data[offset]);
//        offset += lengthBytes;
//
//        // Check if the remaining data is sufficient for the specified length
//        if (offset + length > data.length) {
//            throw new IllegalArgumentException("Specified length exceeds available data length");
//        }
//
//        byte[] valueBytes = readValue(data, offset, length);
//        String value = bytesToHex(valueBytes);
//
//        TLV tlv = new TLV(tag, length, value);
//
//        if (isConstructed(tag)) {
//            int valueOffset = 0;
//            while (valueOffset < valueBytes.length) {
//                TLV child = parseTLV(valueBytes, valueOffset);
//                tlv.addChild(child);
//                valueOffset += child.getLength() + calculateTLVHeaderLength(child);
//            }
//        }
//
//        return tlv;
//    }
//
//    private static String readTag(byte[] data, int offset) {
//        StringBuilder tag = new StringBuilder();
//        tag.append(String.format("%02X", data[offset]));
//        if ((data[offset] & 0x1F) == 0x1F) {
//            do {
//                offset++;
//                if (offset >= data.length) {
//                    throw new IllegalArgumentException("Tag parsing exceeds data length");
//                }
//                tag.append(String.format("%02X", data[offset]));
//            } while ((data[offset] & 0x80) == 0x80);
//        }
//        return tag.toString();
//    }
//
//    private static int readLength(byte[] data, int offset) {
//        int firstByte = data[offset] & 0xFF;
//        if (firstByte < 0x80) {
//            return firstByte;
//        } else {
//            int numBytes = firstByte - 0x80;
//            if (offset + numBytes >= data.length) {
//                throw new IllegalArgumentException("Length bytes exceed data length");
//            }
//            int length = 0;
//            for (int i = 0; i < numBytes; i++) {
//                length = (length << 8) | (data[offset + 1 + i] & 0xFF);
//            }
//            return length;
//        }
//    }
//
//    private static int calculateLengthBytes(int firstByte) {
//        if (firstByte < 0x80) {
//            return 1;
//        } else {
//            return firstByte - 0x80 + 1;
//        }
//    }
//
//    private static byte[] readValue(byte[] data, int offset, int length) {
//        if (offset + length > data.length) {
//            throw new IllegalArgumentException("Not enough data to read the value");
//        }
//        byte[] value = new byte[length];
//        System.arraycopy(data, offset, value, 0, length);
//        return value;
//    }
//
//    private static boolean isConstructed(String tag) {
//        int firstByte = Integer.parseInt(tag.substring(0, 2), 16);
//        return (firstByte & 0x20) == 0x20;
//    }
//
//    private static int calculateTLVHeaderLength(TLV tlv) {
//        int tagLength = tlv.getTag().length() / 2;
//        int lengthLength = calculateLengthBytes(tlv.getLength());
//        return tagLength + lengthLength;
//    }
//
//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (byte b : bytes) {
//            sb.append(String.format("%02X", b));
//        }
//        return sb.toString();
//    }
//}
