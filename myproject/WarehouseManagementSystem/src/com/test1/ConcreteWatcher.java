package com.test1;

public class ConcreteWatcher implements Watcher
{

    @Override
    public void update(String str)
    {
        System.out.println(str);
    }

}
