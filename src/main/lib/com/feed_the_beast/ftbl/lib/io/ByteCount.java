package com.feed_the_beast.ftbl.lib.io;

public enum ByteCount
{
    BYTE(1),
    SHORT(2),
    INT(4);

    public final int bytes;

    ByteCount(int i)
    {
        bytes = i;
    }

    public void write(ByteIOStream io, int num)
    {
        switch(this)
        {
            case BYTE:
                io.writeByte(num);
                return;
            case SHORT:
                io.writeShort(num);
                return;
            default:
                io.writeInt(num);
        }
    }

    public int read(ByteIOStream io)
    {
        switch(this)
        {
            case BYTE:
            {
                byte b = io.readByte();
                if(b == -1)
                {
                    return -1;
                }
                return b & 0xFF;
            }
            case SHORT:
            {
                short s = io.readShort();
                if(s == -1)
                {
                    return -1;
                }
                return s & 0xFFFF;
            }
            default:
            {
                return io.readInt();
            }
        }
    }
}