package com.liuhen.workmanager.libflow;

/**
 * @author Seamus.Zhang
 * Create On 2020/11/21
 * Description : 工作节点
 */
public class FlowNode extends AbsNode {


    private IActionCallBack mCallBack;

    public FlowNode(IAction mAction) {
        super(mAction);
    }

    public FlowNode(int mId, IAction mAction) {
        super(mId, mAction);
    }


    @Override
    public void doAction(IActionCallBack mCallBack) {

        this.mCallBack = mCallBack;
        if(mAction != null){
            mAction.doAction(this);
        }

    }

    @Override
    public void finish() {
        if(mCallBack != null){
            mCallBack.actionCompleted();
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        if(mCallBack !=null){
            mCallBack = null;
        }
    }
}
