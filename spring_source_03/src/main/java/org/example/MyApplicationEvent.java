package org.example;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * ClassName:MyApplicationEvent
 * Package:org.example
 * Description: 自定义applicationevent
 *
 * @Date:2021/12/24 12:58
 * @Author:qs@1.com
 */
public class MyApplicationEvent<T> extends ApplicationEvent  {
    private IMyEventType eventType;
    private T msg;

    public MyApplicationEvent(Object source, IMyEventType eventType, T msg) {
        super(source);
        this.eventType = eventType;
        this.msg = msg;
    }

    public IMyEventType getEventType() {
        return eventType;
    }

    public void setEventType(IMyEventType eventType) {
        this.eventType = eventType;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }


}
