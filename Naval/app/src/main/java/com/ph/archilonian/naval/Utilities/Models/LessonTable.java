package com.ph.archilonian.naval.Utilities.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import nl.elastique.poetry.json.annotations.MapFrom;

/**
 * Created by vidalbenjoe on 29/05/16.
 */

@DatabaseTable
public class LessonTable {
    @DatabaseField(id = true, columnName = "id")
    @MapFrom("id")
    private int mId;

    @DatabaseField(columnName = "content")
    @MapFrom("content")
    private String mContent;

    @DatabaseField
    public String Image;
    @DatabaseField
    public String Links;
    @DatabaseField
    public String Semester;
    @DatabaseField
    public String Week;
    @DatabaseField
    public String Title;

    public String getmContent()
    {
        return mContent;
    }

}
