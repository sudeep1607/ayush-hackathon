package com.fts;

import com.fts.hibernate.models.UserInfo;

public class ThreadLocalData
{
    private static ThreadLocal<UserInfo> currentUser = new ThreadLocal<UserInfo>();

    private ThreadLocalData()
    {

    }

    public static UserInfo get()
    {
        return currentUser.get();
    }

    public static void set(UserInfo userData)
    {
        currentUser.set(userData);
    }

}
