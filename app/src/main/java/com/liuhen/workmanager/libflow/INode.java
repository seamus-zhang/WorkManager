package com.liuhen.workmanager.libflow;

/**
 * @author Seamus.Zhang
 * Create On 2020/11/21
 * Description :
 */
public interface INode {

    void doAction(IActionCallBack callBack);

    int getId();

    void finish();

    void recycle();

}
