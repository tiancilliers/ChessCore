package com.robotsquid.chesscore.entry;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableDate implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Date data;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TableDate(String s) throws ParseException
    {
        data = sdf.parse(s);
    }

    public TableDate(Date d)
    {
        data = d;
    }


    public Date getData()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return data == null ? "" : sdf.format(data);
    }
}
