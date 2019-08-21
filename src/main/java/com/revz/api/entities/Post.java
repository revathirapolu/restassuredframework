package com.revz.api.entities;

public class Post
{
    private String id;
    private String title;
    private String body;
    private String userId;
    public String getId ()
    {
        return id;
    }
    public void setId (String id)
    {
        this.id = id;
    }
    public String getTitle ()
    {
        return title;
    }
    public void setTitle (String title)
    {
        this.title = title;
    }
    public String getBody ()
    {
        return body;
    }
    public void setBody (String body)
    {
        this.body = body;
    }
    public String getUserId ()
    {
        return userId;
    }
    public void setUserId (String userId)
    {
        this.userId = userId;
    }
    @Override
    public String toString()
    {
        return "Post [id = "+id+", title = "+title+", body = "+body+", userId = "+userId+"]";
    }

}