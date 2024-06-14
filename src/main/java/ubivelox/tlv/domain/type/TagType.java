package ubivelox.tlv.domain.type;

public enum TagType {
    PRIMITIVE,
    CONSTRUCTED;

    public static TagType fromByte(byte b) {
        return (b & 0x20) == 0x20 ? CONSTRUCTED : PRIMITIVE;
    }
}
